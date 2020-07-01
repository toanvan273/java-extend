package itx.examples.akka.cluster.sshsessions;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import itx.examples.akka.cluster.sshsessions.client.SshClientService;
import itx.examples.akka.cluster.sshsessions.client.SshClientServiceImpl;
import itx.examples.akka.cluster.sshsessions.cluster.SshClusterManager;
import itx.examples.akka.cluster.sshsessions.cluster.SshClusterManagerImpl;
import itx.examples.akka.cluster.sshsessions.cluster.SshClusterManagerActor;
import itx.examples.akka.cluster.sshsessions.sessions.SshLocalManager;
import itx.examples.akka.cluster.sshsessions.sessions.SshLocalManagerImpl;
import itx.examples.akka.cluster.sshsessions.sessions.SshLocalManagerActor;
import itx.examples.akka.cluster.sshsessions.sessions.SshSessionFactory;
import itx.examples.akka.cluster.sshsessions.sessions.ssh.SshSessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by juraj on 3/18/17.
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private ActorSystem actorSystem;
    private ActorRef sshClusterManagerActor;
    private ActorRef sshLocalManagerActor;
    private SshClientService sshClientService;

    private SshClusterManagerImpl sshClusterManager;
    private SshLocalManagerImpl sshLocalManager;
    private SshSessionFactory sshSessionFactory;

    public Application(ActorSystem actorSystem, SshSessionFactory sshSessionFactory) {
        this.actorSystem = actorSystem;
        this.sshSessionFactory = sshSessionFactory;
    }

    public void init() {
        LOG.info("Application init ...");

        sshClusterManager = new SshClusterManagerImpl();
        sshClusterManagerActor = actorSystem.actorOf(
                Props.create(SshClusterManagerActor.class, sshClusterManager), Utils.CLUSTER_MANAGER_NAME);

        sshLocalManager = new SshLocalManagerImpl(sshSessionFactory);
        sshLocalManagerActor = actorSystem.actorOf(
                Props.create(SshLocalManagerActor.class, sshLocalManager), Utils.LOCAL_MANAGER_NAME);

        sshClientService = new SshClientServiceImpl(actorSystem, sshClusterManager);

        LOG.info("Application done.");
    }

    public void destroy() {
        LOG.info("Application shutdown.");
        sshClusterManagerActor.tell(PoisonPill.getInstance(), ActorRef.noSender());
        sshLocalManagerActor.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

    public SshClientService getSshClientService() {
        return sshClientService;
    }

    public SshLocalManager getSshLocalManager() {
        return sshLocalManager;
    }

    public SshClusterManager getSshClusterManager() {
        return sshClusterManager;
    }

    public static void main(String[] args) {
        LOG.info("Application start ...");
        if (args.length < 1) {
            LOG.error("Error: expected first parameter /path/to/akka.conf file");
            System.exit(1);
        }
        File akkaConfigFile = new File(args[0]);
        if (!akkaConfigFile.exists()) {
            LOG.error("Error: config file not found " + args[0]);
            System.exit(1);
        }
        LOG.info("akkaConfigPath = " + args[0]);
        ActorSystem actorSystem = ActorSystem.create(Utils.CLUSTER_NAME, ConfigFactory.parseFile(akkaConfigFile));
        SshSessionFactory sshSessionFactory = new SshSessionFactoryImpl();
        Application application = new Application(actorSystem, sshSessionFactory);
        application.init();

        Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    public void run() {
                        LOG.info("shutting down ...");
                        application.destroy();
                        actorSystem.terminate();
                        LOG.info("bye.");
                    }
                }
        );

        LOG.info("started.");
    }

}
