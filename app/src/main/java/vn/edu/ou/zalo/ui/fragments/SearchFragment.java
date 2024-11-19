package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.activities.ChatActivity;
import vn.edu.ou.zalo.ui.fragments.adapters.SearchAdapter;
import vn.edu.ou.zalo.ui.fragments.listeners.OnFriendClickListener;
import vn.edu.ou.zalo.ui.states.SearchUiState;
import vn.edu.ou.zalo.ui.viewmodels.AuthViewModel;
import vn.edu.ou.zalo.ui.viewmodels.FriendshipViewModel;
import vn.edu.ou.zalo.ui.viewmodels.SearchViewModel;

@AndroidEntryPoint
public class SearchFragment extends Fragment implements OnFriendClickListener {
    @Inject
    SearchViewModel searchViewModel;
    @Inject
    FriendshipViewModel friendshipViewModel;
    @Inject
    AuthViewModel authViewModel;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private User signedInUser = new User();

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.top_app_bar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.menu_item_action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        assert searchView != null;
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchViewModel.search(newText);
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.fragment_search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        friendshipViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.isLoading() || uiState.getErrorMessage() != null) {
                return;
            }
            if (uiState.getFriendshipStatuses() == null) {
                return;
            }
            if (searchAdapter != null) {
                searchAdapter.setStatusMap(uiState.getFriendshipStatuses());
            }
        });

        authViewModel.getSignedInUser();
        authViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.isLoading() || uiState.getErrorMessage() != null) {
                return;
            }
            signedInUser = uiState.getCurrentSignedInUser();
            if (searchAdapter != null) {
                searchAdapter.setSignedInUser(uiState.getCurrentSignedInUser());
            } else if (recyclerView.getAdapter() != null) {
                searchAdapter.setSignedInUser(uiState.getCurrentSignedInUser());
            }
        });

        return view;
    }

    private void updateUi(SearchUiState searchUiState) {
        if (searchUiState.isLoading()) {
            return;
        }

        if (searchUiState.getUsers() != null && !searchUiState.getUsers().isEmpty()) {
            friendshipViewModel.checkFriendStatuses(searchUiState.getUsers());
        }

        if (recyclerView.getAdapter() == null) {
            searchAdapter = new SearchAdapter(searchUiState.getUsers(), this);
            recyclerView.setAdapter(searchAdapter);
        } else {
            searchAdapter.updateSearchResult(searchUiState.getUsers());
        }
    }

    @Override
    public void onAddFriendClick(User friend) {
        if (!Objects.equals(friend.getId(), signedInUser.getId())) {
            friendshipViewModel.addFriend(friend);
        }
    }

    @Override
    public void onItemClick(User friend) {
        if (!friend.getId().equals(signedInUser.getId())) {
            startActivity(ChatActivity.newIntent(getActivity(), friend));
        }
        // TODO: navigate to profile screen
    }
}
