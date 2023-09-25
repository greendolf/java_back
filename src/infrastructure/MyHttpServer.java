package infrastructure;
//mapper
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class MyHttpServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHttpHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println(t.getRequestURI().getQuery());
            IController ic = new httpController();
            String res;
            if (t.getRequestMethod().equals("GET")) {
                res = ic.handle(t.getRequestMethod(), t.getRequestURI().toString(), t.getRequestURI().getQuery());
            } else if (t.getRequestMethod().equals("POST")) {
                InputStream is = t.getRequestBody();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                res = ic.handle(t.getRequestMethod(), t.getRequestURI().toString(), br.readLine());
            } else {
                res = null;
            }
            System.out.println(res);
            if (res != null) {
                t.sendResponseHeaders(200, res.length());
                OutputStream os = t.getResponseBody();
                os.write(res.getBytes());
                os.close();
            }
        }
    }
}