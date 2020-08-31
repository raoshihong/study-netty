package com.rao.study.bio;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClientTest {
    public static void main(String[] args) throws Exception{
        //创建一个客户端
        Socket socket = new Socket();
        //连接到端口为6666的服务器
        socket.connect(new InetSocketAddress(6666));

        //当服务端有响应时，就会返回给客户端，客户端就可以继续执行

        //客户端向服务端输出数据
        OutputStream os = socket.getOutputStream();
        os.write("hello".getBytes());
    }
}
