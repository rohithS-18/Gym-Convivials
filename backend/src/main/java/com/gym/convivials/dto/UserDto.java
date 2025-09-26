package com.gym.convivials.dto;


public class UserDto {
        private int userId;

        public int getUserId() {
                return userId;
        }

        public void setUserId(int userId) {
                this.userId = userId;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPasword() {
                return pasword;
        }

        public void setPasword(String pasword) {
                this.pasword = pasword;
        }

        public String getCity() {
                return city;
        }

        public void setCity(String city) {
                this.city = city;
        }

        public String getProfilePic() {
                return profilePic;
        }

        public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
        }

        private String username;
        private String email;
        private String pasword;
        private String city;
        private String profilePic;

}
