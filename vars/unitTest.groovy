def call(Map config) {
    sh '''
      echo "Running unit tests..."
      mvn test
    '''
}
