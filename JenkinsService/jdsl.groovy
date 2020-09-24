pipelineJob('job-dsl-demo1') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/Malokingi/NumTheFun')
          }
          branch('*/master')
        }
      }
      lightweight()
    }
  }
}