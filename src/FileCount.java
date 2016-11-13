package src;

import akka.actor.ActorSystem;

class FileCount {

    private final int count;
    private final ActorSystem actorSystem;

    FileCount(int count, ActorSystem actorSystems) {
        this.count = count;
        this.actorSystem = actorSystems;
    }

    int getCount() {
        return count;
    }

    ActorSystem getActorSystem() {
        return actorSystem;
    }
}
