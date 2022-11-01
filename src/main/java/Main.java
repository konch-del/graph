import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ClientException, ApiException, IOException, InterruptedException, ClassNotFoundException {
        ApiVk apiVk = new ApiVk();
        apiVk.init();
        GraphUtils.printGraphInSVG(apiVk.getGraph(), "C:\\Users\\igor-\\IdeaProjects\\graph\\graph_execute.svg");
        GraphUtils.printGraphInVZ(apiVk.getGraph(), "C:\\Users\\igor-\\IdeaProjects\\graph\\graph_execute.vz");
        GraphUtils.saveGraph(apiVk.getGraph(), "C:\\Users\\igor-\\IdeaProjects\\graph\\graph_execute.ser");
        GraphUtils.printGraphInCSV(apiVk.getGraph(), "C:\\Users\\igor-\\IdeaProjects\\graph\\graph_execute.csv");
        GraphUtils.printStats(apiVk.getGraph(), "C:\\Users\\igor-\\IdeaProjects\\graph\\stats_execute.txt");
    }
}