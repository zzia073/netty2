package com.study.base.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import thrift.generated.PersonService;

/**
 * @author ：fei
 * @date ：Created in 2019/11/19 0019 17:05
 *
 * Thrift 传输格式
 * TBinaryProtocol - 二进制格式。
 * TCompactProtocol - 压缩格式。
 * TJSONProtocol - JSON格式
 * TSimpleJSONProtocol - 提供JSON只写协议，生成的文件很容易通过脚本语言解析。
 * TDebugProtocol - 使用移动的刻度的文本格式，以便于debug
 *
 * Thrift 数据传输方式
 * TSocket - 阻塞式 socket
 * TFramedTransport - 以 frame 为单位进行传输，非阻塞式服务中使用
 * TFileTransport - 以文件形式进行传输
 * TMemoryTransport - 将内存用于I/O。Java实现时内部实际使用了简单的ByteArrayOutputStream。
 * TZlibTransport - 使用zlib进行压缩，与其他传输方式联合使用。当前无Java实现。
 *
 * Thrift 支持的服务模型
 * TSimpleServer - 简单的单线程服务模型，常用于测试
 * TThreadPoolServer - 多线程服务模型，使用标准的阻塞式IO。
 * TNonblockingServer - 多线程服务模型，使用非阻塞式IO（需使用TFramedTransport 数据传输方式）
 * THsHaServer - THsHa 引入了线程池去处理，其模型把读写任务放到线程池去处理；Half-sync/Half-async 的处理模式，
 * Hals-async 是在处理IO事件上（accept/read/write io),Half-sync 用于handler对rpc的同步处理
 *
 * 好的组合方式是 TCompactProtocol - TFramedTransport - THsHaServer
 */
public class ThriftServer {
    public static void main(String[] args) throws Exception {
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
        //协议层用到的对象（此处用的是压缩协议）
        arg.protocolFactory(new TCompactProtocol.Factory());
        //此处用的什么传输客户端要用同样的传输，传输层用到的对象
        arg.transportFactory(new TFramedTransport.Factory());

        arg.processorFactory(new TProcessorFactory(processor));
        //半同步半异步的服务
        TServer server = new THsHaServer(arg);
        System.out.println("Thrift Server Started!");
        server.serve();
    }
}
