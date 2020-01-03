package com.openclassrooms.netapp.Controllers.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.netapp.Controllers.Models.GithubUser;
import com.openclassrooms.netapp.R;

import java.util.List;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserViewHolder>
{
    private List<GithubUser> githubUsers;

    public GithubUserAdapter(List<GithubUser> githubUsers)
    {
        this.githubUsers = githubUsers;
    }

    //Création d'un viewHolder à partir du xml représentant chaque ligne
    @Override
    public GithubUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //CREER LA VUE (viewHolder) et faire le inflate de son Layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);
        return new GithubUserViewHolder(view);
    }

    //onBindViewHolder() est appelée pour chacune des lignes visibles.
    //Ici il s'agit de mettre à jour l'apparence de chque ligne.
    @Override
    public void onBindViewHolder(GithubUserViewHolder holder, int position)
    {
        holder.updateWithGithubUser(this.githubUsers.get(position));
    }

    //Retourne le nombre total d'élément de la liste
    @Override
    public int getItemCount()
    {
        return this.githubUsers.size();
    }
}
