package service;

import persistance.UserDao;
import persistance.model.User;
import service.Exception.InvalidUserException;
import service.Exception.InvalidUsernameException;

import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean checkUser(String username, String password) throws InvalidUserException {
        for(User user: userDao.list()){
            if((user.getUserName().equals(username)) && (user.getPassword().equals(password))){
               return true;
            }
        }
        throw new InvalidUserException();
    }

    public User getUser(String userName,String password){
        for(User listUser: userDao.list()){
            if(listUser.getUserName().equals(userName) && listUser.getPassword().equals(password)){
                return listUser;
            }
        }
        return null;
    }

    public void addNewUser(User user) throws InvalidUsernameException {
        for(User listUser: userDao.list()){
            if(listUser.getUserName().equals(user.getUserName())){
                throw new InvalidUsernameException();
            }
        }
        userDao.save(user);
    }

    public void getAllUser(){
        List<User> list = userDao.list();
        for(User user : list){
            System.out.println(user.getId() +" "+ user.getUserName() +" "+ user.getAdmin());
        }
    }
    public void makeUserAdmin(String userName){
        User updateUser = null;
        for(User user: userDao.list()){
            if(user.getUserName().equals(userName)){
                updateUser = user;
                updateUser.setAdmin(true);
                userDao.save(updateUser);
            }
        }
    }

    public void setDiscount(User user){
       user.setDiscount(true);
        userDao.save(user);
    }

    public void deleteDiscount(User user){
        user.setDiscount(false);
        userDao.save(user);
    }
}
