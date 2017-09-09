package com.github.mobile;

import org.eclipse.egit.github.core.client.GitHubClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;

/**
 * Default client used to communicate with GitHub API
 */
public class DefaultClient extends GitHubClient {

    private static final String USER_AGENT = "ForkHub/1.2";

    /**
     * Create client
     */
    public DefaultClient() {
        super();

        setSerializeNulls(false);
        setUserAgent(USER_AGENT);
    }

    /**
     * Create connection to URI
     *
     * @param uri
     * @return connection
     * @throws IOException
     */
    @Override
    protected HttpURLConnection createConnection(String uri) throws IOException {
        OkUrlFactory factory = new OkUrlFactory(new OkHttpClient());
        URL url = new URL(createUri(uri));
        return factory.open(url);
    }
}
