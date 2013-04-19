package com.stormpath.sdk.client;

/**
 * Class StagingClientBuilder is used to create a ClientBuilder pointing to a different environment
 * than production
 *
 * @author josebarrueta
 * @since 2013/04/18
 */
public class StagingClientBuilder extends ClientBuilder{

    private String stagingBaseUrl;


    public void setStagingBaseUrl(String stagingBaseUrl) {

        this.stagingBaseUrl = stagingBaseUrl;

        if(this.stagingBaseUrl != null){
            super.setBaseUrl(this.stagingBaseUrl);
        }
    }


}
