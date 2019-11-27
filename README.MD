### Netty 学习

##### I.I/O系统
   1. 输入流和输出流
      - 参照物是程序
      - 在最底层所有的输入/输出都是字节形式的。字符流只是为了处理字符更方便提供的
      - 对照关系
      
   | Byte Streams | Character Streams |  
   |    :---:     |     :---:         |
   | InputStream  |     Reader        |
   | OutputStream |     Writer        |
   
   ---
   
##### II.读写逻辑
   读取数据的逻辑为：
      
      open a stream
      while more information
      read information
      close the stream
   
   写出数据的逻辑为：
   
      open a stream
      while more information
      write information
      close the stream
   节点流：从特定的地方读写的流类，例如：磁盘或一块儿内存区域。（FileInputStream）
   
   过滤流：使用节点流作为输入或输出。过滤流是使用一个已经存在的输入流或输出流连接创建的。（BufferedInputStream）   
   
                   |-----FileInputStream 
                   |-----ByteArrayInputStream  |-----DataInputStream
                   |-----FilterInputStream ←---|-----BufferedInputStream
    InputStream←---|-----ObjectInputStream     |-----LineNumberInputStream
                   |-----PipedInputStream      |-----PushbackInputStream
                   |-----SequenceInputStream
                   |-----StringBufferInputStream
   
    
                   |-----FileOutputStream
                   |-----ByteArrayOutputStream |-----DataOutputStream
    OutputStream←--|-----FilterOutputStream ←--|-----BufferedOutputStream   
                   |-----ObjectOutputStream    |-----PrintStream
                   |-----PipedOutputStream
   
 ##### III.I/O流的链接
   I. Input Stream Chain
   
   从文件中获取输入字节　　　　　增加了缓冲的功能　　　　增加了读取java基本数据类型的功能
   
   FileInputStream　　　------→　　BufferedInputStream　　------→　　DataInputStream
   
   II.Output Stream Chain
   
   可以往输出流中写入java基本数据类型　　　提供数据写入到缓冲区的功能　　将数据写入到文件中
   
   DataOutputStream　　------→　　bufferedOutputStream　　-----→　　FileOutputStream
   
##### IV.装饰者模式（Decorator）
   + 装饰者模式扩展《对象》的功能
   + 装饰者模式的角色
      + 抽象构建角色（Component）-- InputStream
      + 具体构建角色（Concrete Component） -- FileInputStream (节点流)
      + 装饰角色（Decorator） -- FilterInputStream(过滤流接口)
      + 具体装饰角色（Concrete Decorator) -- BufferedInputStream（过滤流）
   + 装饰者模式的特点：
      + 装饰对象和真是对象有相同的接口。
      + 装饰对象包含一个真是对象的引用。
      + 装饰对象接收所有来自客户端的请求。然后转发给真实对象
      + 装饰对象可以在转发这些请求以前或以后增加一些附加功能。
      
   + 装饰模式和继承的区别
      + 装饰模式
         + 用来扩展特定对象的功能
         + 不需要子类
         + 动态
         + 运行时分配职责
         + 防止由于子类而导致的复杂和混乱
         + 更多的灵活性
         + 对于一个给定的对象，同时可能有不同的装饰对象，客户端可以通过他的需要选择合适的装饰对象发送消息。
      + 继承
         + 用来扩展一类对象的功能
         + 需要子类
         + 静态
         + 编译时分派职责
         + 导致很多子类产生
         + 缺乏灵活性
#### I.NIO
   1. java.io和nio 的区别
      + java.io  中最核心的概念是流（Stream），面向流编程。一个流要么是输入流要么是输出流，不可能两者都是。
      + java.nio 中最核心的概念是选择器（Selector），通道（Channel），缓冲区（Buffer），面向块（block）或者缓冲区（buffer）编程。
      + Buffer本身就是一块内存，底层实现上就是一个数组。数据的读写都是通过buffer实现的。Buffer承载了读和写两个操作。
      + Channel中的数据必须通过buffer才能读入程序或程序中的数据必须通过buffer才能写出到Channel
      + 除了数组之外，Buffer还提供了对于数据的结构化访问方式，并且可以追踪到系统的读写过程。
      + Java中8中原生数据类型除了Boolean都有各自对应的Buffer类型，如IntBuffer等
      + 与Stream不同的是，Channel是双向的，一个流只可能是InputStream或是OutputStream，Channel打开后则可以进行读取、写入或是读写。
      + 由于Channel是双向的，因此它能更好地反映出底层操作系统的真实情况；在Linux系统中，底层操作系统的通道就是双向的。
      
   2. Buffer
      + buffer 中属性的含义
         + capacity buffer的底层数组的容量，不能为负值且不可变。
         + limit buffer中第一个不能读或写的位置，不能为负值且不能大于capacity
         + position buffer中position下一个位置的元素将被读或写，不能大于limit
         + 0 <= mark <= position <= limit <= capacity
      + buffer 中重要的方法
         + allocate();创建一个buffer，一般创建一个HeapXXXBuffer堆上创建一个buffer,调用父类的方法去构建
         + get();put();都会是position++
         + flip();limit = position; position = 0;
         + clear();limit = capacity; position = 0;
         + get;put;相对方法：limit和position值会在操作时被考虑到。相对方法：limit和position被忽略掉。
         + slice();方法截取position到limit位置形成一个新的buffer，与原有buffer共享底层数组
         + readonlyBuffer();获取一个只读的buffer，共享原有buffer底层数组，
         实际的类HeapByteBufferR在put方法上直接抛异常，原有的是HeapByteBuffer
         + wrap();用一个底层的数组创建一个buffer，共享一份数据，这种方式去创建buffer尽量使用创建好的buffer去修改内容
         + allocateDirect();创建一个DirectXXXBuffer直接缓冲，通过底层的方式在堆外的内存中创建一个buffer
            + 在堆外创建一个直接缓冲区，要想使java程序访问到则必须提供一个指针或者引用方式去访问，DirectXXXBuffer是java的一个
         对象，他继承自Buffer，在Buffer中有个long类型的字段address，该字段是专门用来访问直接缓冲区数据的。
         之所以将该字段放在Buffer不放在DirectXXXBuffer中是为了提高本地调用的速度。
            + 操作系统要想操作java缓存中的数据，如果分配的是堆上的HeapXXXBuffer那么操作系统会将该缓冲区的内容复制到
         操作系统的工作内存中，这样的话就多了一次数据拷贝的过程，如果用堆外内存则操作系统可以直接操作该部分数据，
         这种方式叫zero copy 零拷贝
      + ScatteringByteChannel (只读channel) 和 GatheringByteChannel (只写channel) 实现这两个接口的类可以通过向buffer数组中读写
      实现分块读写。比如，http请求的时候请求头和请求体用两个不同的buffer来接收。
   3. Selector 网络编程多路复用在java中的体现，Selector相当于linux的Epoll
   4. 编码问题
      + ASCII 7bit表示一个字符，共计可以表示128个字符 48--0,65--A,97--a
      + ISO-8859-1 8 bit表示一个字符，即用一个字节表示一个字符。共计256个字符
      它是用ASCII扩展来的，所以它是兼容ASCII的
      + GB2312 对所有的汉字进行了编码，用两个字节表示一个汉字，不包含生僻字
      + GBK 国标扩展，完全兼容GB2312
      + big5 台湾制定的
      + unicode 统一字符集，统一采用两个字节表示一个字符。\uxxxx表示
         + unicode 出现的原因是太多的国家制定太多的字符集导致传输过程编码混乱
         + unicode 的问题是很多国家只需要使用比如ASCII之类的字符集就够了，一个字节就足以表示一个字符，
         而它却用两个字节来表示，本来1M的存储文件就得用2M来存储，浪费了太多的空间，于是就出现了UTF-8。
      + UTF,Unicode Translation Format unicode转换格式
         + unicode是编码方式，utf是存储方式；
         + UTF-8，UTF-16等是Unicode的一种实现方式
         + UTF-16LE(little endian),UTF-16BE(big endian) 在文件存储的开始加一个标识
         + UTF-8 变长字节表示形式，一般来说UTF-8会通过3个字节来表示中文，英文也是用一个字节表示
         + BOM(Byte Order Mark),字节序标识，windows的遗留问题（大端小端问题）
      