package vn.edu.ou.zalo.ui.viewmodels;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.SearchUserUseCase;
import vn.edu.ou.zalo.ui.states.SearchUiState;

public class SearchViewModel extends ViewModel {
    private static final long SEARCH_DEBOUNCE_DELAY = 500;

    private final MutableLiveData<SearchUiState> uiState = new MutableLiveData<>(new SearchUiState(false, null, new ArrayList<>()));
    private final SearchUserUseCase searchUserUseCase;
    private final Handler searchHandler = new Handler();
    private Runnable searchRunnable;

    @Inject
    public SearchViewModel(SearchUserUseCase searchUserUseCase) {
        this.searchUserUseCase = searchUserUseCase;
    }

    public LiveData<SearchUiState> getUiState() {
        return uiState;
    }

    public void search(String query) {
        if (searchRunnable != null) {
            searchHandler.removeCallbacks(searchRunnable);
        }

        searchRunnable = () -> {
            uiState.setValue(new SearchUiState(true, null, new ArrayList<>()));

            searchUserUseCase.execute(Map.of("query", query), new IDomainCallback<List<User>>() {
                @Override
                public void onSuccess(List<User> data) {
                    uiState.setValue(new SearchUiState(false, null, data));
                }

                @Override
                public void onFailure(Exception e) {
                    uiState.setValue(new SearchUiState(false, e.getMessage(), new ArrayList<>()));
                }
            });
        };

        searchHandler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY);
    }
}
