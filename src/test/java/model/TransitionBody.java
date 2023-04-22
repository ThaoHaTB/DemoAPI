package model;

public class TransitionBody {

    private Transition transition;
    public TransitionBody(Transition transition) {
        this.transition = transition;
    }
    public Transition getTransition() {
        return transition;
    }
    public static class Transition {
        private String id;

        public Transition(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }


}
