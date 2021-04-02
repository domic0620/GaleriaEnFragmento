package edu.co.icesi.galeriaenfragmento;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {

    //Variable global para la galería
    public static final int GALLERY_CALLBACK = 11;

    //UI
    private ImageView image;
    private Button openGal;

    //Estado
    private String path = null;

    public GalleryFragment() {
        // Required empty public constructor
    }


    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        openGal = view.findViewById(R.id.openGal);
        image = view.findViewById(R.id.image);

        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            image.setImageBitmap(bitmap);
        }

        openGal.setOnClickListener(
                v->{
                    Intent j = new Intent(Intent.ACTION_GET_CONTENT);
                    j.setType("image/*");
                    startActivityForResult(j, GALLERY_CALLBACK);
                }
        );

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == GALLERY_CALLBACK){
            Uri uri = data.getData();
            //Aquí el contexto es getActivity() porque this es un fragment
            //Además se setea el estado, porque si el fragment desaparece,
            //la imagen puede seguirse viendo gracias a setImage del onCreateView
            path = UtilDomi.getPath(getActivity(), uri);
            Bitmap imagebm = BitmapFactory.decodeFile(path);
            image.setImageBitmap(imagebm);
        }
    }
}