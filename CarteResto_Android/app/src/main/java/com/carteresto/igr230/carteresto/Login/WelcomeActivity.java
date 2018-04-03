package com.carteresto.igr230.carteresto.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.ProductModel;
import com.carteresto.igr230.carteresto.R;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 欢迎界面
 *
 * @author WelcomeActivity
 */
public class WelcomeActivity extends AppCompatActivity {
    static final String TAG = WelcomeActivity.class.getSimpleName();
    @BindView(R.id.login_progress)
    ProgressBar mProgressBar;
    private ProductsRepository mRepo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        // 设置窗体无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.welcome);
        ButterKnife.bind(this);

        mRepo = ProductsRepository.getInstance(getApplication());


        Initialization calcul=new Initialization();
        calcul.execute();




    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private class Initialization extends AsyncTask<Void, Pair<Integer, String>, Void> implements OnSuccessListener<FileDownloadTask.TaskSnapshot> {
        private Pair<Integer, String> msg = new Pair<>(0,"");
        @Override
        protected Void doInBackground(Void... voids) {
//            downLoadJson();
            mRepo.initDataBase();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            Snackbar.make(mProgressBar, R.string.inite, Snackbar.LENGTH_SHORT);
        }


        public Initialization() {
            super();
        }

        @Override
        protected void onProgressUpdate(Pair<Integer, String>... values) {
            super.onProgressUpdate(values);
            Pair<Integer, String> msg = values[0];
            mProgressBar.setProgress(msg.first);
            Snackbar.make(mProgressBar, msg.second, Snackbar.LENGTH_SHORT);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        public void downLoadJson() {
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://carterestoandroid.appspot.com");
            StorageReference storageRef = storage.getReference("products.json");

            final File file = new File(getApplication().getFilesDir(), "products.json");
            storageRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: File is successful created");
                    publishProgress(new Pair<Integer, String>(1, "Successful to download data base"));
                    createDataBase(file);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e(TAG, "Can not download the products.json.");
                }
            });
        }


        private void createDataBase(File file) {
            Integer i = 1;
            try(FileReader fr = new FileReader(file)){
                Gson gson = new Gson();
                List<ProductModel> proList = Arrays.asList(gson.fromJson(fr, ProductModel[].class));
                mRepo.getProductDao().insertProductList(proList);
//                Log.d(TAG, "onSuccess: Init database with list size:" + proList.size());
//                StorageReference storage = FirebaseStorage.getInstance("gs://carterestoandroid.appspot.com").getReference("product");
//                String rootPath = getApplication().getFilesDir().getPath();
//
//                for(Product product: proList){
//                    File parent = new File(rootPath + "/" + product.getId());
//                    product.setImage(parent.getAbsolutePath());
//                    parent.mkdirs();
//                    Long counter = mRepo.getProductDao().insert(product);
//                    Log.e(TAG, "createDataBase: All size" + counter + " items in sql");
//
//                    File smallFile = new File(parent.getAbsolutePath(), "small.jpg");
//                    storage.child(product.getId() + "/small.jpg").getFile(smallFile)
//                            .addOnSuccessListener(taskSnapshot -> Log.d(TAG, "onSuccess: " + smallFile.getAbsolutePath()))
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.e(TAG, "onFailure: " + e.getMessage() + "\n\t" + product.getId() );
//                        }
//                    });
//                    File bigFile = new File(parent.getAbsolutePath(), "big.jpg");
//                        storage.child(product.getId()+"/big.jpg").getFile(bigFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                Log.d(TAG, "onSuccess: " + bigFile.getAbsolutePath());
//                                publishProgress(new Pair<>(0, "Download file " + bigFile.getAbsolutePath()));
//                            }
//                        });
//                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch(Exception e){

            }
        }



        @Override
        protected void onPostExecute(Void voids) {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }



        @Override
        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

        }
    }
}