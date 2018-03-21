package com.carteresto.igr230.carteresto;

import android.content.Context;
import android.support.annotation.NonNull;

import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.carteresto.igr230.carteresto.source.local.ProductDatabase;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by zhufa on 21/03/2018.
 */

//public class Injection {
//    public static ProductsRepository provideTasksRepository(@NonNull Context context) {
//        checkNotNull(context);
//        ProductDatabase database = ProductDatabase.getInstance(context);
//        return ProductsRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
//                TasksLocalDataSource.getInstance(new AppExecutors(),
//                        database.taskDao()));
//    }
//}
