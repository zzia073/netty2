package com.study.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author ：fei
 * @date ：Created in 2019/12/2 0002 17:30
 * 配置运行时vm参数 -ea 使断言生效
 * Netty的HeadChannelContextHandler 和 TailChannelContextHandler 负责释放ByteBuf的引用计数
 */
public class ReferenceCountTest {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.directBuffer();
        assert buf.refCnt() == 1;

        buf.retain();
        assert buf.refCnt() == 2;

        boolean destroyed = buf.release();
        assert !destroyed;
        assert buf.refCnt() == 1;

    }
}
