package com.richard.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.util.UriComponents;

/**
 * Created by highl on 04/07/2016.
 */
public class LinkHelper {

        private static final String hostname;

        public LinkHelper() {
        }

        public static LinkHelper.LinkHelperBuilder linkDirectlyTo(Object invocationValue) {
            return new LinkHelper.LinkHelperBuilder(ControllerLinkBuilder.linkTo(invocationValue));
        }

        static {
            try {
                Process e = Runtime.getRuntime().exec("hostname");
                e.waitFor();
                InputStreamReader inputStreamReader = new InputStreamReader(e.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                if((line = bufferedReader.readLine()) != null) {
                    hostname = line;
                    bufferedReader.close();
                    e.destroy();
                } else {
                    throw new RuntimeException("No response from process execution");
                }
            } catch (Exception var4) {
                throw new RuntimeException("Unable to determine hostname", var4);
            }
        }

        public static class LinkHelperBuilder {
            private final ControllerLinkBuilder builder;

            private LinkHelperBuilder(ControllerLinkBuilder builder) {
                this.builder = builder;
            }

            public Link withRel(String rel) {
                return new Link(this.toUriComponents().toUriString(), rel);
            }

            public Link withSelfRel() {
                return this.withRel("self");
            }

            public URI toUri() {
                return this.toUriComponents().toUri();
            }

            private UriComponents toUriComponents() {
                return this.builder.toUriComponentsBuilder().host(LinkHelper.hostname).build();
            }
        }
    }



