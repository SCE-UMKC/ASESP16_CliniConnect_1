angular.module('app.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider
    
  

      .state('tabsController.home', {
    url: '/page2',
    views: {
      'tab1': {
        templateUrl: 'templates/home.html',
        controller: 'homeCtrl'
      }
    }
  })

  .state('tabsController.appointmentReminder', {
    url: '/page3',
    views: {
      'tab3': {
        templateUrl: 'templates/appointmentReminder.html',
        controller: 'appointmentReminderCtrl'
      }
    }
  })

  .state('tabsController.sendLabs', {
    url: '/page4',
    views: {
      'tab2': {
        templateUrl: 'templates/sendLabs.html',
        controller: 'sendLabsCtrl'
      }
    }
  })

  .state('tabsController', {
    url: '/page1',
    templateUrl: 'templates/tabsController.html',
    abstract:true
  })

  .state('tabsController.registerPatient', {
    url: '/page6',
    views: {
      'tab4': {
        templateUrl: 'templates/registerPatient.html',
        controller: 'registerPatientCtrl'
      }
    }
  })

  .state('tabsController.clinicInformation', {
    url: '/page7',
    views: {
      'tab5': {
        templateUrl: 'templates/clinicInformation.html',
        controller: 'clinicInformationCtrl'
      }
    }
  })

$urlRouterProvider.otherwise('/page1/page2')

  

});