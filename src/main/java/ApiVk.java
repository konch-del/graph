import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.IOException;
import java.util.List;

public class ApiVk {

    Graph<Integer, DefaultEdge> graph;
    static int userId = 132011950;

    public void init() throws ClientException, ApiException, IOException, InterruptedException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        //авторизация вк
        UserActor actor = new UserActor(132011950, accessToken);
        //запрос на друзей
        FriendsGetQuery getFriendsForStartUser = vk.friends().get(actor).userId(userId).listId(25);
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
                for (Integer idFriendForFriends : friendsForFriends){
                    if(!graph.containsVertex(idFriendForFriends)) {
                        graph.addVertex(idFriendForFriends);
                    }
                    if(!graph.containsEdge(id, idFriendForFriends)) {
                        graph.addEdge(id, idFriendForFriends);
                    }
                    //костыль, чтобы не ругался вк
                    Thread.sleep(1000);
                }
            }catch (Exception ignored){

            }
        }
    }

    public Graph<Integer, DefaultEdge> getGraph(){
        return graph;
    }

}