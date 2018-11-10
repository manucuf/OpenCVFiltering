package opencvproject;

public interface IFilter {

    void onSnapshot(FilterType filter);
    boolean onSaving();
    void onDiscarding();

}
