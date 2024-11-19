package vn.edu.ou.zalo.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class User extends BaseModel implements Parcelable {
    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    private String fullName;
    private String phoneNumber;
    private long birthdate;
    private Gender gender;
    private String bio;
    private String avatarUrl;
    private String backgroundUrl;
    private long lastLogin;
    private boolean isOnline;
    private long friendCount;
    private String hashedPassword;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public long getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(long friendCount) {
        this.friendCount = friendCount;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                ", bio='" + bio + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", backgroundUrl='" + backgroundUrl + '\'' +
                ", lastLogin=" + lastLogin +
                ", isOnline=" + isOnline +
                ", friendCount=" + friendCount +
                ", id=" + getId() +
                '}';
    }

    public User() {
    }

    protected User(Parcel in) {
        setId(in.readString());

        fullName = in.readString();
        phoneNumber = in.readString();
        birthdate = in.readLong();
        gender = Gender.valueOf(in.readString());
        bio = in.readString();
        avatarUrl = in.readString();
        backgroundUrl = in.readString();
        lastLogin = in.readLong();
        isOnline = in.readByte() != 0;
        friendCount = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());

        dest.writeString(fullName != null ? fullName : "");
        dest.writeString(phoneNumber != null ? phoneNumber : "");
        dest.writeLong(birthdate);
        dest.writeString(gender != null ? gender.name() : "UNKNOWN");
        dest.writeString(bio != null ? bio : "");
        dest.writeString(avatarUrl != null ? avatarUrl : "");
        dest.writeString(backgroundUrl != null ? backgroundUrl : "");
        dest.writeLong(lastLogin);
        dest.writeByte((byte) (isOnline ? 1 : 0));
        dest.writeLong(friendCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}


