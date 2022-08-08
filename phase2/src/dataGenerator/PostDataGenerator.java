package dataGenerator;

import entities.Post;
import gateway.IWriter;
import gateway.Writer;

import java.util.HashMap;

import java.util.UUID;


public class PostDataGenerator {
    public static void main(String[] args) {
        IWriter writer1 = new Writer("data/postData.txt");
        IWriter writer2 = new Writer("test/testData/testPostData.txt");
        HashMap<UUID, Post> map = new HashMap<>();
        writer1.write(map);
        writer2.write(map);
    }
}
