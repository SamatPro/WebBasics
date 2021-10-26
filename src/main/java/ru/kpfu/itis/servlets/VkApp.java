package ru.kpfu.itis.servlets;

//import com.vk.api.sdk.client.TransportClient;
//import com.vk.api.sdk.client.VkApiClient;
//import com.vk.api.sdk.client.actors.UserActor;
//import com.vk.api.sdk.exceptions.ApiException;
//import com.vk.api.sdk.exceptions.ClientException;
//import com.vk.api.sdk.httpclient.HttpTransportClient;
//import com.vk.api.sdk.objects.users.Fields;
//import com.vk.api.sdk.objects.users.responses.GetResponse;
//
//import java.util.List;
//import java.util.stream.Collectors;

//public class VkApp {
//    public static void main(String[] args) {
//        TransportClient transportClient = HttpTransportClient.getInstance();
//        VkApiClient vk = new VkApiClient(transportClient);
//
//        //создадим ссылку
//        // https://oauth.vk.com/authorize?client_id=7984735&redirect_uri=http://ourlocalhost&scope=wall&scope=friends&v=5.131
//        UserActor actor = new UserActor(81538298, "1fb1e7f51ab87ae987702f974dc29461d8c8aa6c0fab8944b4b896841b22806b5c3fbafb27d17b0282169");
//
//        try {
//            List<Integer> friendIds = vk.friends().getOnline(actor).execute();
//            System.out.println(friendIds.toString());
//            List<GetResponse> users = vk.users().get(actor)
//                    .userIds(friendIds.stream().map(userId -> userId.toString()).collect(Collectors.toList()))
//                    .fields(Fields.EDUCATION)
//                    .execute();
//
//            for (GetResponse user: users) {
//                System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getFacultyName());
//            }
//
//
//
//        } catch (ApiException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}
