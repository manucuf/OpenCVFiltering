package opencvproject;

public interface IFilter {

    void onFilterApplied(FilterType filter);
    boolean onSaving();
    void onDiscarding();

}
