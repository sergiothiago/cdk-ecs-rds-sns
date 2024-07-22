package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.events.targets.SnsTopic;
import software.amazon.awscdk.services.ses.actions.Sns;
import software.amazon.awscdk.services.sns.Topic;
import software.amazon.awscdk.services.sns.subscriptions.EmailSubscription;
import software.constructs.Construct;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

//TODO: Instancia com problema do SnsStack
public class SnsStack extends Stack {

    private SnsTopic productEventTopic;

    public SnsStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public SnsStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        SnsTopic productEventTopic = SnsTopic.Builder.create(Topic.Builder.create(this, "ProductEventTopic")
                .topicName("product-events")
                .build())
                .build();

        productEventTopic.getTopic()
                .addSubscription(EmailSubscription.Builder.create("sergiothiagovrb@gmail.com").json(true).build());


    }

    public SnsTopic getProductEventTopic() {
        return productEventTopic;
    }
}
