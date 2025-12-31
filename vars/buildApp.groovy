def call(Map config) {
    sh '''
      echo "Building application..."
      mvn clean package -DskipTests
    '''
}
