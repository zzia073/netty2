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
#### II.Netty

   1. 零拷贝（复制操作，比如用户访问服务器请求html文件，传输过程，将服务器的html文件用磁盘转成data传给用户）
      + read()操作步骤：UserSpace --read()sysCall--> KernelSpace --ask for data-->Hardware
      --DMA--> KernelSpace --copy data to use --> UserSpace 一次用户空间到内核空间的切换一次内核空间
      到用户空间的切换，一次硬盘到内核空间的数据拷贝，一次内核空间到用户空间的数据拷贝
      + write()操作步骤：UserSpace --write()sysCall--> kernelSpace(socket buffer)--write data-->
      netUser --done--> UserSpace 一次用户空间调用write到内核空间的切换，一次内核空间write完成到用户空间
      的切换
      + 以上的两个操作，copy过程经过了四次用户内核空间的切换，而数据到用户空间中只是暂存了一下并没有操作
      + 零拷贝步骤：UserSpace --sendFile()sysCall--> KernelSpace -- ask for data--> Hardware
      -- DMA --> kernelSpace -- write to socket buffer--> KernelSpace --write to net--> Kernel
      -- done --> UserSpace 一次用户空间到内核空间切换，一次内核空间到用户空间的切换，中间的所有操作在内核
      空间进行，操作系统意义上的零拷贝，不会再有内核空间向用户空间拷贝数据
      + 以上的操作在内核空间中还是进行了一次数据拷贝，先是从硬件拷贝到内核空间，后再从内核空间拷贝到socket缓冲区
      + 如果操作系统支持scattering和gathering会进一步减少copy时间，对于socket buffer可以通过指针探知到内核空间中
      位置的数据，并不需要拷贝数据到socket buffer，只需要将文件描述符拷贝过去就行了
      + 文件内存映射，将文件映射到内核空间中，操作系统提供对应的方法可以使用户直接操作文件。
      + 最终的零拷贝时序：userSpace --sendFile()sysCall--> KernelSpace -- ask data--> Hardware
      -- DMA copy --> kernelBuffer --CPU copy(fd,拷贝文件描述符，就是kernel中文件从哪读，读多少)-->
      socketBuffer -- DMA copy(有fd就可以用gathering从多个地方读) --> protocolEngine(协议引擎，发送给网络)
   
   2. netty启动过程中几个重要的类和方法
      + EventLoopGroup 最终调用的是 MultithreadEventExecutorGroup 的构造方法，会创建一个executor
        new ThreadPerTaskExecutor(newDefaultThreadFactory()); ThreadFactory 将线程的创建和线程要执行的任务解耦
        Executor jdk1.5提供的一个对象，只有一个方法execute(command);命令模式设计方法，将命令command定义好交给Executor，
      + ServerBootStrap 简单的启动类什么都没做，netty提供的辅助类，更方便的启动netty服务
      + group()方法给AbstractServerBootStrap的属性赋值
      + handler方法是用于group方法的第一个参数，childHandler是用于第二个参数
      + bind()方法用于启动服务器
         + doBind() -- initAndRegister();反射创建启动类的channel方法中传入的类
         + Future 代表的是异步计算的结果 netty提供的Future中sync()方法会等待Future完成，同时添加了一些addListener方法
         利用观察者模式，当Future完成的时候会去调用listener中的operationComplete方法传入Future，同时isSuccess和isDone分开
          ```
          ChannelFuture 完成示意图
                                                +---------------------------+
                                                | Completed successfully    |
                                                +---------------------------+
                                           +---->      isDone() = true      |
           +--------------------------+    |    |   isSuccess() = true      |
           |        Uncompleted       |    |    +===========================+
           +--------------------------+    |    | Completed with failure    |
           |      isDone() = false    |    |    +---------------------------+
           |   isSuccess() = false    |----+---->      isDone() = true      |
           | isCancelled() = false    |    |    |       cause() = non-null  |
           |       cause() = null     |    |    +===========================+
           +--------------------------+    |    | Completed by cancellation |
                                           |    +---------------------------+
                                           +---->      isDone() = true      |
                                                | isCancelled() = true      |
                                                +---------------------------+
          ```
         + ChannelFuture的等待方法await之类的超时并不能表示Channel的操作已完成，I/O超时的情况ChannelFuture会被标记为失败完成，
         上图最右侧Completed with failure，一般在BootStrap启动类连接的时候设置选项连接超时来判定，不可以用future的等待方法
         设置超时时间
         + initAndRegister中创建了channel之后给channel中设置对应属性，然后把pipeline中添加上handler方法中的传入的处理器，同时
         用ServerBootstrapAcceptor 把客户端的channel交由workerGroup去管理
         + ChannelPipeLine 是ChannelHandler的集合，按顺序执行里边的所有Handler Inbound -- read() Outbound -- write()
         + BossGroup和WorkerGroup分开因为accept方法只需要执行一次连接建立后就不需要执行，
         而read和write需要执行多次来读写客户端传来和返回的数据
         + ServerSocketChannel在创建的时候会创建一个ServerSocketChannelConfiguration 配置对象，
         其中会创建一个自适应字节数组分配器
         + AdaptiveRecvByteBufAllocator 根据上次读入缓冲区的字节数来去决定下次缓冲区大小分配
         + bind之后的sync()方法是同步为了确保bind方法执行完成后获取对应的ChannelFuture对象一定是绑定完成的
   3. reactor 模式 反应器模式
      + 日志处理器处理流程 连接阶段
         + 服务端 1. register handler() 注册Acceptor 到 Initiation Dispatcher 中
         + 服务端 2. handle_events() 启动 Initiation Dispatcher 调用
         + 服务端 3. select() 阻塞 Initiation Dispatcher 调用
         + 客户端 4. connect() 请求
         + 服务端 5. handle_event() 事件发生 交给Acceptor
         + 服务端 6. Acceptor accept() 7. create() 创建一个Handler 
         + 服务端 8. register handler() 注册handler到Initiation Dispatcher 中
      + 日志服务器处理流程 发送阶段
         + 客户端 1. send() 客户端向服务器端发送数据到 Initiation Dispatcher
         + 服务端 2. handle event() 初始化派发器找到对应Handler
         + 服务端 3. Handler 处理 recv() 4. write() 5. return() 返回到 Initiation Dispatcher 中 返回给客户
      + Reactor模式五大角色分析
         1. Handler(句柄或是描述符)：本质上表示一种资源，是由操作系统提供的；该资源用于表示一个个的事件，比如说文件描述符，
         或是针对网络编程中的Socket描述符。事件即可以来自于外部，也可以来自于内部；外部事件比如说客户端的连接请求，客户端发送过来
         数据等；内部事件比如说操作系统产生的定时器事件等。它本质上就是一个文件描述符。Handle 是时间产生的发源地。
         2. Synchronous Event Demultiplexer(同步事件分离器)：它本身是一个系统调用，用于等待事件的发生（事件可能是一个，也可能
         是多个）。调用方在调用它的时候会被阻塞，一直阻塞到同步事件分离器上有事件产生为止。对于Linux来说，同步时间分离器指的就是
         常用的I/O多路复用机制，比如说select、poll、epoll等。在Java NIO 领域中，同步时间分离器对应的组件就是Selector;对应的阻塞
         方法就是select方法
         3. Event Handler（事件处理器）： 本身由多个回调方法构成，这些回调方法构成了与应用相关的对于某个事件的反馈机制。Netty相比于
         Java NIO 来说，在事件处理器这个角色上进行了一个升级，它为我们开发者提供了大量的回调方法，供我们在特定事件产生时实现相应的
         回调方法进行业务逻辑的处理
         4. Concrete Event Handler（具体事件处理器）：是事件处理器的实现。它本身实现了事件处理器所提供的的各种回调方法，从而实现了
         特定于业务逻辑。它本质上就是我们所编写的一个个处理器的实现
         5. Initiation Dispatcher（初始化分发器）：相当于Reactor角色就是netty中的EventLoop，它本身定义了一些规范，这些规范用于
         事件的调度方式，同时又提供了应用进行事件处理器的注册、删除等设施。它本身是整个事件处理器的核心所在，Initiation Dispatcher
         会通过同步事件分离器来等待事件的发生。一旦事件发生，Initiation Dispatcher首先会分理处每一个事件，然后调用事件处理器，最后
         调用相关的回调方法处理这些事件。
         ```
         //每个事件处理器会拥有描述符信息、同时能处理各种发生的事件
         Event Handler --owns--> handle 
         //初始分发器开始用同步事件分离器把事件处理器注册上
         Initiation Dispatcher --use--> Synchronous Event Demultiplexer  
         Event Handler --register_handler(h)|remove_handler(h)--> Initiation Dispatcher
         //初始分发器用同步事件分离器调用它的select方法阻塞等待获取事件发生
         select();
         //事件发生 调用具体的事件处理器的方法去处理事件
         Concrete Event Handler --implement--> Event Handler
         foreach h in handlers
            h.handle_event();
         end loop
         ```
      + Reactor模式流程
         1. 当应用向Initiation Dispatcher注册具体的事件处理器时，应用会标识出该事件处理器希望Initiation Dispatcher在某个事件
         发生时向其通知的该事件，该事件与Handle关联。
         2. Initiation Dispatcher会要求每个事件处理器向其传递内部的Handle。该Handle向操作系统标识了事件处理器。
         3. 当所有的事件处理器注册完毕后，应用会调用handle_events 方法来启动Initiation Dispatcher的事件循环。这时，Initiation
         Dispatcher会将每个注册的事件管理器的Handle合并起来，并使用同步事件分离器等待这些事件的发生。比如说，TCP协议层会使用select
         同步事件分离器操作等待客户端发送的数据到达连接的socket handle上。
         4. 当与某个事件源对应的Handle变为ready状态时（比如说，TCP socket变为等待读状态时），同步事件分离器会通知Initiation
         Dispatcher。
         5. Initiation Dispatcher会触发事件处理器的回调方法，从而响应这个处于ready状态的Handle。当事件发生时，Initiation 
         Dispatcher会被事件源激活的Handle作为『Key』来寻找并分发恰当的事件处理器回调方法。
         6. Initiation Dispatcher会回调事件处理器的handle_events回调方法来执行特定于应用的功能。（开发者自己所编写的功能），从而
         响应这个事件。所发生的的事件类型可以来作为该方法参数并被该方法内部使用来执行额外的特定于服务的分离或分发。
   4. Netty重要的类
      + ChannelPipeline
         + ChannelPipeline 实现了Intercepting Filter 拦截过滤器模式，它和Channel互相包含内部都有对方的引用
         + pipeLine.addLast(group,"handler",new MyBusinessLogicHandler());用事件循环组group去执行可能会阻塞io线程的业务逻辑
         + ChannelPipeline 是线程安全的
         + pipeline 中的addXXX方法在Channel被注册后，添加HandlerContext会调用对应Handler的handlerAdd()方法
      + ChannelOption
         + channelOption的作用是配置ChannelConfig
         + channelOption并不维护值的信息，提供了set和get方法去获取值的内容
         + ChannelOption和AttributeKey都是继承自Constant，且是单例的，都可以用等号的方式比较。
         + ConstantPool就是用一个ConcurrentHashMap来存储Constant
         + Constant接口的实现类都是用来做key的，值是通过其他方式设置的
      + AttributeKey|Attribute
         + AttributeMap 中存放着AttributeKey和Attribute 用于存放业务数据
      + ChannelConfig
         + 表示对整个channel的配置信息
         + channel中设置的各种配置信息和属性都是被维护在Channel的内部属性中，option在config里，attribute在父类的attributeMap中
      + ChannelHandlerContext 是ChannelPipeline和ChannelHandler交互的桥梁
         + 一个ChannelHandler可以被多次添加到ChannelPipeLine 中，一个ChannelHandler可以属于多个ChannelHandlerContext
         + 对handler的多次添加有个属性isSharable如果是true则可以被多次添加，否则属性added被添加过则抛出异常，不可再次添加
         + ChannelHandler每向pipeline中添加一次都会生成一个新的ChannelHandlerContext与之对应
         + handler向pipeline中添加就相当于把handler包装成context对象的双向链表中的一个元素增删一样
      + ChannelInitializer 
         + 它的initChannel中可以在Channel注册到EventLoop的时候把一些Handler放入channel中
         + 它是个inboundHandler
         + 在netty的childHandler()方法中会传入一个该类，用来添加Handler
         + 启动的过程中也会添加一个ChannelInitializer用来将Acceptor组件添加到所有Handler的最后
         + 该类实现了InboundHandler但是它的作用是给pipeline中添加Handler，也就是initChannel方法调用，
         因此他在添加完Handler之后就是initChannel方法返回的时候会被移除出pipeline
      + SingleThreadEventLoop
      + ChannelFuture|ChannelPromise promise是提供了可以写入的future对象，给future增加set方法
      + Unsafe NioMessageUnsafe 用来注册通道到Selector上的类
   5. Netty EventLoop总结
      1. 一个EventLoopGroup 当中会包含一个或多个EventLoop
      2. 一个EventLoop 在它的整个生命周期中都只会与唯一一个Thread进行绑定。
      3. 所有由EventLoop所处理的各种I/O事件都将在它所关联的那个Thread上进行处理。
      4. 一个Channel在它的整个生命周期中只会注册在一个EventLoop上。
      5. 一个EventLoop 在运行过程当中，会被分配给一个或者多个Channel。
      6. 重要结论：在netty中，channel的实现一定是线程安全的；基于此，我们可以存储一个Channel的引用，并且在需要向远程断点发送数据是，
      通过这个引用来调用Channel的相应方法；即便当时有很多线程都在使用它也不会出现多线程问题；而且，消息一定会被按照顺序发送出去。
      7. 重要结论：我们在业务开发中，不要将长时间执行的耗时任务放入到EventLoop的执行队列中，因为它将会一直阻塞该线程所对应的所有
      Channel上的其他执行任务，如果我们需要进行阻塞调用或是耗时的操作（实际开发中很常见），那么我们就需要使用一个专门的EventExecutor
      （业务线程池）。
         + 通常会有两种实现方式：
         + 在ChannelHandler的回调方法中，使用自己定义的业务线程池，这样就可以实现异步调用。
         + 借助于Netty提供的像ChannelPipeline添加ChannelHandler时调用的addLast方法来传递EventExecutor
         + 说明：默认情况下（调用addLast(handler)）,ChannelHandler中的回调方法都是由I/O线程所执行，如果调用了
         ChannelPipeline addLast(EventExecutorGroup group,ChannelHandler... handlers);方法，那么ChannelPipeline
         中的回调方法就是又参数中的group线程组来执行的。
      ```
        //register 在AbstractChannel中的注册方法上这个判断
        //如果执行当前方法的线程Thread.currentThread()==eventLoop.thread和EventLoop中的唯一线程是同一个
        //那么直接注册Channel，否则把注册作为一个任务提交给当前的eventLoop去执行，这样始终是一个线程在操作，不会有多线程问题
        //所有的Handler处理操作都是通过这一个线程去调用的，这些操作中如果有一个阻塞了，所有的其他的都得等待执行
        //而所有Channel的Handler操作都会在这里等待，因此一定不能有长时间的I/O操作在handler里处理
          if (eventLoop.inEventLoop()) {
              register0(promise);
          } else {
              try {
                  eventLoop.execute(new Runnable() {
                      @Override
                      public void run() {
                          register0(promise);
                      }
                  });
              } catch (Throwable t) {
                  logger.warn(
                          "Force-closing a channel whose registration task was not accepted by an event loop: {}",
                          AbstractChannel.this, t);
                  closeForcibly();
                  closeFuture.setClosed();
                  safeSetFailure(promise, t);
              }
          }
      ```
