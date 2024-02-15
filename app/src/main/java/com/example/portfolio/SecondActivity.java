package com.example.portfolio;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class SecondActivity extends AppCompatActivity {
    StorageReference storageReference;
    LinearProgressIndicator linearProgressIndicator;
    Uri image;
    ProgressBar pb;
    MaterialButton select,save,show;
    EditText nameT;
    EditText ageT;
    EditText passT;
    EditText strengthT;
    EditText weaknessT;
    private long timestamp;


    //Entry point for firebase to access data
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference dataref=db.collection("Users").document("data");
    private CollectionReference collectionReference= db.collection("Users");

    private ActivityResultLauncher<Intent>activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){

                if(result.getData()!=null) {
                    image = result.getData().getData();
                    save.setEnabled(true);

                }else{
                    Toast.makeText(SecondActivity.this, "Please Select the image!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        FirebaseApp.initializeApp(SecondActivity.this);
        storageReference= FirebaseStorage.getInstance().getReference();
        select=findViewById(R.id.imageselect);
        save=findViewById(R.id.button);
        pb=findViewById(R.id.pbar);
        nameT=findViewById(R.id.editTextText);
        ageT=findViewById(R.id.editTextText2);
        passT=findViewById(R.id.editTextTextPassword);
        strengthT=findViewById(R.id.editTextText3);
        weaknessT=findViewById(R.id.editTextText4);
        show=findViewById(R.id.showbtn);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            Intent intent=new Intent(getApplicationContext(), ThirdActivity.class);
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                                data dt = snapshot.toObject(data.class);
                               // Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                                // Assuming your data class has the appropriate getters or public fields
                                intent.putExtra("nam", dt.getName());
                                intent.putExtra("ag", dt.getAge());
                                intent.putExtra("pass", dt.getPassword());
                                intent.putExtra("stre", dt.getStrength());
                                intent.putExtra("weak", dt.getWeakness());
                                intent.putExtra("imageUrl", dt.getImageUrl()); // Pass the image URL if needed
                                startActivity(intent);
                            } else {
                                Toast.makeText(SecondActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata(image);
            }
        });
    }

    private void savedata(Uri image) {
      //  reference to path
        String name=nameT.getText().toString();
        String age=ageT.getText().toString();
        String password=passT.getText().toString();
        String strength=strengthT.getText().toString();
        String weakness=weaknessT.getText().toString();







        StorageReference references=storageReference.child("images/"+ UUID.randomUUID().toString());//name is random string
        references.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                references.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl=uri.toString();
                        data dataobj=new data(name,age,password,strength,weakness,imageUrl);
                        dataobj.setTimestamp(new Date().getTime());
                        collectionReference.add(dataobj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String docid=documentReference.getId();
                            }
                        });
                    }
                });

                Toast.makeText(SecondActivity.this, "details Uploaded sucessfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SecondActivity.this, "Sorry Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                pb.setMax(Math.toIntExact(snapshot.getTotalByteCount()));
                pb.setProgress(Math.toIntExact(snapshot.getBytesTransferred()));
            }
        });





    }
}