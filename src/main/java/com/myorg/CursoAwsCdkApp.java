package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;

import java.util.Arrays;

/**
 * Serviço ECS com Cluster e VPC pré configurados, 2 pods iniciais podendo aumentar até 4. Health check já configurado
 * @Author Sergio Mendonça
 * base original by @siecola.
 */
public class CursoAwsCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        // VPC
        VpcStack vpcStack = new VpcStack(app, "Vpc");

        // CLUSTER
        ClusterAwsCdkStack clusterAwsCdkStack =  new ClusterAwsCdkStack(app, "Cluster", vpcStack.getVpc());
        clusterAwsCdkStack.addDependency(vpcStack);

        RdsStack rdsStack = new RdsStack(app, "Rds", vpcStack.getVpc());
        rdsStack.addDependency(vpcStack);

        SnsStack snsStack = new SnsStack(app, "Sns");

        // SERVIÇO ECS COM FARGATE + AUTOSCALING 2 PODS INICIAIS PODENDO AUOMENTAR ATÉ 4.
        Service01Stack service01Stack = new Service01Stack(app, "Service01", clusterAwsCdkStack.getCluster());
        service01Stack.addDependency(clusterAwsCdkStack);
        service01Stack.addDependency(rdsStack);


        app.synth();
    }
}


