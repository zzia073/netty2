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