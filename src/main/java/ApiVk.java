import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.scoring.EigenvectorCentrality;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ApiVk {

    Graph<Integer, DefaultEdge> graph;
    static int userId = 132011950;

    public void init() throws ClientException, ApiException, IOException, InterruptedException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        //авторизация вк
        UserActor actor = new UserActor(132011950, accessToken);
        //запрос на друзей
        FriendsGetQuery getFriendsForStartUser = vk.friends().get(actor).userId(userId);
        //костыль, чтобы не ругался вк
        Thread.sleep(1000);
        graph = new SimpleGraph<>(DefaultEdge.class);
        graph.addVertex(userId);
        List<Integer> friends = getFriendsForStartUser.execute().getItems();
        //добавление друзей начального юзера
        for(Integer id : friends){
            if(!graph.containsVertex(id)) {
                graph.addVertex(id);
            }
            if(!graph.containsEdge(userId, id)) {
                graph.addEdge(userId, id);
            }
            //костыль, чтобы не ругался вк
            Thread.sleep(1000);
            //обработка исключения со стороны АПИ
            try {
                //добавление друзей друзей
                List<Integer> friendsForFriends = vk.friends().get(actor).userId(id).execute().getItems();
                for (Integer idFriendsForFriends : friendsForFriends){
                    if(!graph.containsVertex(idFriendsForFriends)) {
                        graph.addVertex(idFriendsForFriends);
                    }
                    if(!graph.containsEdge(id, idFriendsForFriends)) {
                        graph.addEdge(id, idFriendsForFriends);
                    }
                }
            }catch (Exception ignored){

            }
        }
    }
    //Центральность по близости
    public void closenessCentrality(){
        ClosenessCentrality<Integer, DefaultEdge> closenessCentrality = new ClosenessCentrality<>(graph);
        Map<Integer, Double> scores = closenessCentrality.getScores();
        System.out.println(Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey());
    }
    //Центральность по посреднечеству
    public void betweennessCentrality(){
        BetweennessCentrality<Integer, DefaultEdge> betweennessCentrality = new BetweennessCentrality<>(graph);
        Map<Integer, Double> scores = betweennessCentrality.getScores();
        System.out.println(Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey());
    }
    //Центральность по собственному вектору
    public void eigenvectorCentrality(){
        EigenvectorCentrality<Integer, DefaultEdge> eigenvectorCentrality = new EigenvectorCentrality<>(graph);
        Map<Integer, Double> scores = eigenvectorCentrality.getScores();
        System.out.println(Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey());
    }
}