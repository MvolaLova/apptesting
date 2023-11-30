package org.login;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.List;

public class AzureAuthentification {
    public static void main(String[] args) {
        String clientId = "a4d523cb-5de0-4ae5-a5dc-a7143ec955aa";
        String clientSecret ="xXd8Q~_bjiyOluQV_xwrIZRQrYYr0BwZ1lMLjbnH";
        // String tenantId = "ecfe764c-c17b-4553-a5eb-59f8bd560b51";
        String tenantId = "315d3779-aa9b-4ae7-bdee-633d076a4e97";
        // String authority = "https://login.microsoftonline.com/" + tenantId + "/";

        List<String> scopes = List.of("https://graph.microsoft.com/.default")   ;
        List<String> graphApiScopes = List.of("Mail.Read","User.read");

        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .clientId(clientId).tenantId(tenantId).clientSecret(clientSecret).build();

        if (null == credential) {
            try {
                throw new Exception("Unexpected error");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(scopes , credential);

        GraphServiceClient<Request> graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider).buildClient();

        // Use the 'me' endpoint for the signed-in user
        User user = graphClient.users("bfc6170a-b630-42ca-a8b9-1e071cb0238e").buildRequest().get();

        // Now you can access user properties
        System.out.println("Display Name: " + user.displayName);
        System.out.println("Email: " + user.mail);

    }
}
