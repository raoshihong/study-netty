package com.rao.study.bio;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServerTest {
    public static void main(String[] args) throws Exception{
        testMulClient();
    }

    /**
     * 通过线程池创建多个线程
     * @throws Exception
     */
    public static void testMulClient() throws Exception{

        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //服务端创建一个ServerSocket
        ServerSocket serverSocket = new ServerSocket();
        //服务端指定绑定端口
        serverSocket.bind(new InetSocketAddress(6666));
        while (true){
            //等待客户端连接,客户端连接上则获取客户端Socket对象
            final Socket socket = serverSocket.accept();
            System.out.println("你有新的客户端连接上来");
            //每连接上一个客户端,则服务端开启一个线程与之通信
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        //在各自线程中处理客户端请求
                        handler(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public static void handler(Socket socket)throws Exception{
        System.out.println(Thread.currentThread().getId()+"---"+Thread.currentThread().getName());
        InputStream is = socket.getInputStream();
        int length = -1;
        byte[] buff = new byte[1024];
        //等待客户端输入数据
        while ((length = is.read(buff))!=-1){
            System.out.println(new String(buff,0,length));
        }
    }
}


//    /**
//     * 测试单个客户端
//     * @throws Exception
//     */
//    public static void testSingleClient() throws Exception{
//        //服务端创建一个ServerSocket
//        ServerSocket serverSocket = new ServerSocket();
//        //服务端指定绑定端口
//        serverSocket.bind(new InetSocketAddress(6666));
//        //这种写法只能有一个客户端连接上服务端
//        //等待客户端连接,客户端连接上则获取客户端Socket对象
//        Socket socket = serverSocket.accept();
//        //如果没有客户端连接上来，则会被阻塞在accept位置，下面的代码不会被执行,只有客户端连接上来了,才能执行下面的代码
//        //通过客户端socket获取客户端的输出流
//        OutputStream os = socket.getOutputStream();
//        os.write("hello".getBytes());
//
//        //服务端获取客户端的输入流
//        InputStream is = socket.getInputStream();
//
//        int length = -1;
//        byte[] buff = new byte[1024];
//
//        //当读取不到客户端数据时，会阻塞在这块
//        while ((length = is.read(buff))!=-1){
//            System.out.println(new String(buff,0,length));
//        }
//    }

