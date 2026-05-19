package com.xoriant.web;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaForwardController {

    private final Path indexHtmlPath;

    public SpaForwardController(@Value("${xarvis.ui.static-path}") String staticPath) {
        this.indexHtmlPath = Path.of(staticPath, "index.html");
    }

    @GetMapping("/")
    public ResponseEntity<Resource> index() {
        return serveIndexHtml();
    }

    @GetMapping({
            "/{first:^(?!api$|sso$|oidc$|auth$|resource3$|admin$|react$|ioExport$|v3$|swagger-ui$|swagger-resources$|hs$|wf$)[^\\.]*$}",
            "/{first:^(?!api$|sso$|oidc$|auth$|resource3$|admin$|react$|ioExport$|v3$|swagger-ui$|swagger-resources$|hs$|wf$)[^\\.]*$}/{second:[^\\.]*}",
            "/{first:^(?!api$|sso$|oidc$|auth$|resource3$|admin$|react$|ioExport$|v3$|swagger-ui$|swagger-resources$|hs$|wf$)[^\\.]*$}/{second:[^\\.]*}/{third:[^\\.]*}",
            "/{first:^(?!api$|sso$|oidc$|auth$|resource3$|admin$|react$|ioExport$|v3$|swagger-ui$|swagger-resources$|hs$|wf$)[^\\.]*$}/{second:[^\\.]*}/{third:[^\\.]*}/{fourth:[^\\.]*}",
            "/{first:^(?!api$|sso$|oidc$|auth$|resource3$|admin$|react$|ioExport$|v3$|swagger-ui$|swagger-resources$|hs$|wf$)[^\\.]*$}/{second:[^\\.]*}/{third:[^\\.]*}/{fourth:[^\\.]*}/{fifth:[^\\.]*}"
    })
    public ResponseEntity<Resource> spa() {
        return serveIndexHtml();
    }

    private ResponseEntity<Resource> serveIndexHtml() {
        FileSystemResource indexHtml = new FileSystemResource(indexHtmlPath);
        if (!indexHtml.exists() || !indexHtml.isReadable()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(indexHtml);
    }
}
