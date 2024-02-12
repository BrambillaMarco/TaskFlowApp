package it.appventurers.taskflow.util;

import android.app.Application;

import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.data.source.BaseRemoteData;
import it.appventurers.taskflow.data.source.BaseRemoteUserAuth;
import it.appventurers.taskflow.data.source.RemoteData;
import it.appventurers.taskflow.data.source.RemoteUserAuth;

public class ClassBuilder {

    private static ClassBuilder CLASS_BUILDER = null;

    private ClassBuilder(){}

    public static synchronized ClassBuilder getClassBuilder() {
        if (CLASS_BUILDER == null) {
            CLASS_BUILDER = new ClassBuilder();
        }
        return CLASS_BUILDER;
    }

    public UserRepository getUserRepository(Application application) {
        BaseRemoteUserAuth baseRemoteUserAuth = new RemoteUserAuth();
        return new UserRepository(baseRemoteUserAuth);
    }

    public DataRepository getDataRepository(Application application) {
        BaseRemoteData baseRemoteData = new RemoteData();
        return new DataRepository(baseRemoteData);
    }
}
