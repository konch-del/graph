import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ClientException, ApiException, IOException, InterruptedException, ClassNotFoundException {
        ApiVk apiVk = new ApiVk();
        apiVk.init();
        GraphUtils.printGraphInSVG(apiVk.getGraph(), "D:\\java_new\\graph\\graph.svg");
        GraphUtils.printGraphInVZ(apiVk.getGraph(), "D:\\java_new\\graph\\graph.vz");
        GraphUtils.saveGraph(apiVk.getGraph(), "D:\\java_new\\graph\\graph.ser");
        GraphUtils.printStats(apiVk.getGraph(), "D:\\java_new\\graph\\stats.txt");
    }
}