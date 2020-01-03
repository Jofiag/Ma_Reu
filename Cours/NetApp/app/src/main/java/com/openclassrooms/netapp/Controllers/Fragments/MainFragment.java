package com.openclassrooms.netapp.Controllers.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.netapp.Controllers.Models.GithubUser;
import com.openclassrooms.netapp.Controllers.Views.GithubUserAdapter;
import com.openclassrooms.netapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class MainFragment extends Fragment
{
    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView mRecyclerView;

    private Disposable mDisposable;
    private List<GithubUser> mGithubUsers;
    private GithubUserAdapter adapter;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView(); //Appelée lors de la création UI
        //TO DO HERE : Récupération des données sur l'API Github

        return  view;
    }

    private void configureRecyclerView()
    {
        this.mGithubUsers = new ArrayList<>();
        this.adapter = new GithubUserAdapter(this.mGithubUsers);
        //Attache de l'adapteur à la recyclerView
        this.mRecyclerView.setAdapter(this.adapter);
        //Définition du LayoutManager afin d'envoyer au recyclerView la manière d'affichage des données
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
