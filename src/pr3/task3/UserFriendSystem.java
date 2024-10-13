package pr3.task3;

import io.reactivex.rxjava3.core.Observable;

import java.util.*;

record UserFriend(int userId, int friendId) {

    @Override
    public String toString() {
        return "UserFriend{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                '}';
    }
}

public class UserFriendSystem {
    private static final int TOTAL_FRIENDSHIPS = 100;
    private static final UserFriend[] userFriendsArray = new UserFriend[TOTAL_FRIENDSHIPS];
    private static final Random random = new Random();

    public static void main(String[] args) {
        fillUserFriendsArray();

        List<Integer> randomUserIds = generateRandomUserIds(10);

        Observable.fromIterable(randomUserIds)
                .flatMap(Observable::fromArray)
                .flatMap(UserFriendSystem::getFriends)
                .subscribe(userFriend -> System.out.println("Найден друг: " + userFriend));
    }

    public static Observable<UserFriend> getFriends(int userId) {
        return Observable.fromArray(userFriendsArray)
                .filter(userFriend -> userFriend.userId() == userId);
    }

    private static void fillUserFriendsArray() {
        for (int i = 0; i < TOTAL_FRIENDSHIPS; i++) {
            int userId = random.nextInt(10);
            int friendId = random.nextInt(10);
            userFriendsArray[i] = new UserFriend(userId, friendId);
        }
    }

    private static List<Integer> generateRandomUserIds(int size) {
        List<Integer> userIds = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            userIds.add(random.nextInt(10));
        }
        return userIds;
    }
}
