# codingtask
SpringBoot with ActiveMq and MongoDb application to run on Kubernetes

Steps to run the application in Kubernetes 

1. Build the application by gradle 

    gradlew build && java -jar build/libs/gradlecoding-task.jar 

2. Build image by Docker 

  docker build --build-arg JAR_FILE=build/libs/*.jar -t gradlecoding-task .  

3. tag the docker image 

  docker tag <image-id> babli27/gradlecoding-task 

4. push the docker image in docker hub 

  docker push babli27/gradlecoding-task 

5. To Deploy application on Kubernetes 

  kubectl apply -f deployment.yaml 

6.Keep doing kubectl get all until the demo pod shows its status as "Running". 

   kubectl get all. 

7. To connect to the application, which you have exposed as a Service in Kubernetes 

   kubectl port-forward svc/coding-task 8080:8080 
   
   
   Note:Use useraccount  of docker hub instead of babli27
