package demo.model;

import com.plaid.client.PlaidClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//adding the required data to the plaidcleint instance


@Configuration
public class PlaidConfiguration {

    // client id, secret and public key are generated when register in plaid. this data is used to generate an access tocken
    @Value("${PLAID_CLIENT_ID ? :5b51290f4ca9fb0011c5bffe}")
    private String plaidClientId;

    @Value("${PLAID_SECRET ?:3b6e5c84bf8feb3dda6cfdd2f9ff72}")
    private String plaidSecret;

    @Value("${PLAID_PUBLIC_KEY ?:3b6e5c84bf8feb3dda6cfdd2f9ff72}")
    private String plaidPublicKey;

    @Value("#{systemProperties['PLAID_ENV'] ?: 'sandbox'}")
    private String plaidEnv;

    @Bean
    //a recipe for creating actual instances of the class defined by that bean definition.
    // just like a class, you can potentially have many object instances created from a single recipe.

    public PlaidClient plaidClient() {
        PlaidClient plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret(plaidClientId, plaidSecret)
                .publicKey(plaidPublicKey) // optional. only needed to call endpoints that require a public key
                .sandboxBaseUrl() // or equivalent, depending on which environment you're calling into
                .build();
        return plaidClient;
    }
}