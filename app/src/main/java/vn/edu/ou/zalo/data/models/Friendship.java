package vn.edu.ou.zalo.data.models;

public class Friendship extends BaseModel {
    private User sender;
    private int status = 0; // 0 = 'accepted', 1 = 'pending', 2 = 'blocked'
}
