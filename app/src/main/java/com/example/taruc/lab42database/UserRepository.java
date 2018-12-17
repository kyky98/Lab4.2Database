package com.example.taruc.lab42database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

//TODO 6: Create a repository class to manage data query thread


//read from cloud/local, set to viewmodel then display UI
public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    //Access all user record from the local DB
    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);

        userDao = db.userDao();
        allUsers = userDao.loadAllUsers();
    }

    //Update change to the LiveData (View Model)
    LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

    //Call Dao to do
    public void insertUser(User user){
        new insertAsyncTask(userDao).execute(user);
    }

    //<Param, Progress, Results>
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public insertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }

}
