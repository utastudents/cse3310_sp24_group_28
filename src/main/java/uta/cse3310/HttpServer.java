package uta.cse3310;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.freeutils.httpserver.HTTPServer;
import net.freeutils.httpserver.HTTPServer.ContextHandler;
import net.freeutils.httpserver.HTTPServer.FileContextHandler;
import net.freeutils.httpserver.HTTPServer.Request;
import net.freeutils.httpserver.HTTPServer.Response;
import net.freeutils.httpserver.HTTPServer.VirtualHost;

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

public class HttpServer {

    private static final String HTML = "./html";
    int port = 8080;
    String dirname = HTML;

    public HttpServer(int portNum, String dirName) {
        System.out.println("creating http server port " + portNum);
        port = portNum;
        dirname = dirName;
    }

    public void start() {
        System.out.println("in httpd server start");
        try {
            // This File directory creates a File object based on the directory name specified
            File dir = new File(dirname);
            //Checks if the directory actually exists or is readable.
            if (!dir.canRead())
                throw new FileNotFoundException(dir.getAbsolutePath());


            // set up server
            HTTPServer server = new HTTPServer(port);

            // Creates virtualHost
            VirtualHost host = server.getVirtualHost(null); // default host
            host.setAllowGeneratedIndex(true); // with directory index pages

            //Sets an HTTP file context handler at the root URL. 
            // 
            // Possible to request files inside the directory wrongdir by using localhost:9080/filename
            // Example: localhost:9080/index2.html will make a valid GET
            // files within the same folder are visible to each other, this can be utilized 
            // in the scenario that we implement multiple pages.
            host.addContext("/", new FileContextHandler(dir));

            server.start();
            System.out.println("HTTPServer is listening on port " + port);
        } catch (Exception e) {
            System.err.println("error: " + e);
        }

    }

  

}
