package org.softuni.exam.structures;

import org.softuni.exam.entities.User;
import org.softuni.exam.entities.Video;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ViTubeRepositoryImpl implements ViTubeRepository {

    private final Set<User> users;
    private final Set<Video> videos;
    private final Set<User> passiveUsers;

    public ViTubeRepositoryImpl() {
        this.users = new LinkedHashSet<>();
        this.videos = new LinkedHashSet<>();
        this.passiveUsers = new LinkedHashSet<>();
    }

    @Override
    public void registerUser(User user) {
        this.users.add(user);
        this.passiveUsers.add(user);
    }

    @Override
    public void postVideo(Video video) {
        this.videos.add(video);
    }

    @Override
    public boolean contains(User user) {
        return this.users.contains(user);
    }

    @Override
    public boolean contains(Video video) {
        return this.videos.contains(video);
    }

    @Override
    public Iterable<Video> getVideos() {
        return this.videos;
    }

    @Override
    public void watchVideo(User user, Video video) throws IllegalArgumentException {
        if (!this.contains(user) || !this.contains(video)) {
            throw new IllegalArgumentException();
        }
        int views = video.getViews();
        video.setViews(views + 1);

        int userViews = user.getViews();
        user.setViews(userViews + 1);

        this.passiveUsers.remove(user);
    }

    @Override
    public void likeVideo(User user, Video video) throws IllegalArgumentException {
        if (!this.contains(user) || !this.contains(video)) {
            throw new IllegalArgumentException();
        }
        int likes = video.getLikes();
        video.setLikes(likes + 1);

        int userLikes = user.getLikes();
        user.setLikes(userLikes + 1);

        this.passiveUsers.remove(user);
    }

    @Override
    public void dislikeVideo(User user, Video video) throws IllegalArgumentException {
        if (!this.contains(user) || !this.contains(video)) {
            throw new IllegalArgumentException();
        }
        int dislikes = video.getDislikes();
        video.setDislikes(dislikes + 1);

        int userDislikes = user.getDislikes();
        user.setDislikes(userDislikes + 1);

        this.passiveUsers.remove(user);
    }

    @Override
    public Iterable<User> getPassiveUsers() {
        return this.passiveUsers;
    }

    @Override
    public Iterable<Video> getVideosOrderedByViewsThenByLikesThenByDislikes() {
        return this.videos
                .stream()
                .sorted((o1, o2) -> {
                    if (o2.getViews() == o1.getViews()) {
                        if (o2.getLikes() == o1.getLikes()) {
                            return o1.getDislikes() - o2.getDislikes();
                        }
                        return o2.getLikes() - o1.getLikes();
                    }
                    return o2.getViews() - o1.getViews();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<User> getUsersByActivityThenByName() {
        return this.users
                .stream()
                .sorted((o1, o2) -> {
                    if (o2.getViews() - o1.getViews() == 0) {
                        if (Math.max(o2.getLikes(), o2.getDislikes()) - Math.max(o1.getLikes(), o1.getDislikes()) == 0) {
                            return o1.getUsername().compareTo(o2.getUsername());
                        }
                        return Math.max(o2.getLikes(), o2.getDislikes()) - Math.max(o1.getLikes(), o1.getDislikes());
                    }
                    return o2.getViews() - o1.getViews();
                })
                .collect(Collectors.toList());
    }
}
