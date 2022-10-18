import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ClientException, ApiException, IOException, InterruptedException {
        ApiVk apiVk = new ApiVk();
        apiVk.init();
        apiVk.closenessCentrality();
        apiVk.betweennessCentrality();
        apiVk.eigenvectorCentrality();
    }
}
