angular.module('app.controllers', ['jett.ionic.filter.bar', 'ionic-datepicker', 'ionic-timepicker', 'AngularPrint', 'angular-momentjs'])
  
.controller('homeCtrl', function($scope, $ionicFilterBar, $http, $ionicModal, ionicDatePicker, $moment) {
     $scope.shouldShowDelete = true;
     $scope.shouldShowReorder = true;
     $scope.listCanSwipe = true;
    $scope.labSelect = "";
     
    
    var link = '< TODO add_url_here >';
    

        var vm = this,
        items = [],
        filterBarInstance;
    
        $http.get(link).then(function (response){
            for(var i = 0; i < response.data.patients.length; i++){
                
                var item = {
                    patient: response.data.patients[i].name,
                    date: response.data.patients[i].date,
                    dr: response.data.patients[i].dr,
                    dob: $moment(response.data.patients[i].dob).format('MMMM Do, YYYY'),
                    username: response.data.patients[i].username,
                    password: response.data.patients[i].password
                };
                items.push(item);
            }
            items.sort(sort_by("date", false));
            for(var i = 0; i < items.length; i++){
                items[i].date = $moment(items[i].date).format('MMMM Do YYYY, h:mm A')
            }
            
        });

    vm.items = items;
    
    var sort_by = function(field, reverse, primer){

       var key = primer ? 
           function(x) {return primer(x[field])} : 
           function(x) {return x[field]};

       reverse = !reverse ? 1 : -1;

       return function (a, b) {
           return a = key(a), b = key(b), reverse * ((a > b) - (b > a));
         } 
    }
    
    $scope.setVitals = function(dt){
        $scope.vitalItems = dt;
    }
    
    $scope.getVitalItems = function(){
        var vitalItems = [];
        var link = '< TODO add_url_here >'
        + "username=" + $scope.username;
        console.log(link); 
        $http.get(link).then(function(response){
            
            for(var i = 0; i < response.data.labs.length; i++){
                var item = {
                    BP: response.data.labs[i].BP,
                    BS: response.data.labs[i].BS,
                    TEMP: response.data.labs[i].TEMP,
                    FEELING: response.data.labs[i].FEELING,
                    timestamp: response.data.labs[i].timestamp,
                    status: response.data.labs[i].status
                };
                vitalItems.push(item);
            }
            vitalItems.sort(sort_by("timestamp", true));

            
            for(var i = 0; i < vitalItems.length; i++){
                vitalItems[i].timestamp = $moment(vitalItems[i].timestamp).format('MMMM Do YYYY, h:mm A');
            }
            console.log(vitalItems);
            $scope.vitalItems = vitalItems; 
            $scope.setVitals(vitalItems);
            return vitalItems;
        });
        
    }
    
    $scope.setForms = function(fm){
        $scope.pForms = fm;
    }
    
    $scope.getForms = function(){
        var fItems = [];
        var link = '< TODO add_url_here >'
        + "username=" + $scope.username;
        console.log(link);
        
        $http.get(link).then(function(response){
            console.log(response.data);
            
            for(var i = 0; i < response.data.length; i++){
                var item = {
                    username: response.data[i].username,
                    timestamp: response.data[i].timestamp,
                    labNum: response.data[i].labNum,
                    drink: response.data[i].drugs.drink,
                    smoke: response.data[i].drugs.smoke,
                    surgeries: response.data[i].surgeries,
                    meds: response.data[i].meds,
                    tuberculosis: response.data[i].relatives.tuberculosis,
                    diabetes: response.data[i].relatives.diabetes,
                    cancer: response.data[i].relatives.cancer,
                    complaints: response.data[i].complaints,
                    allergies: response.data[i].allergies
                    
                };
                fItems.push(item);
            }
            fItems.sort(sort_by("timestamp", true));
            
            for(var i = 0; i < fItems.length; i++){
                fItems[i].timestamp = $moment(fItems[i].timestamp).format('MMMM Do YYYY, h:mm A');
            }
           
            $scope.setForms(fItems);
            
        });
    }
    
    $scope.setSurvey = function(srv){
        console.log("happy birthday");
        
        $scope.survey = srv;
        console.log($scope.survey);
    }
    
        
    $scope.getSurveys = function(){
        var fItems2 = [];
        var link = '< TODO add_url_here >'
        + "username=" + $scope.username;
        console.log(link);
        
        $http.get(link).then(function(response){
            console.log(response.data);
            
            for(var i = 0; i < response.data.surveys.length; i++){
                var item = {
                    timestamp: response.data.surveys[i].timestamp,
                    q1: response.data.surveys[i].q1,
                    q2: response.data.surveys[i].q2,
                    q3: response.data.surveys[i].q3,
                    q4: response.data.surveys[i].q4,
                    q5: response.data.surveys[i].q5,
                    q6: response.data.surveys[i].q6,
                    q7: response.data.surveys[i].q7,
                    q8: response.data.surveys[i].q8,
                    q9: response.data.surveys[i].q9,
                    q10: response.data.surveys[i].q10
                    
                };
                fItems2.push(item);
            }
            fItems2.sort(sort_by("timestamp", true));
            
            for(var i = 0; i < fItems2.length; i++){
                fItems2[i].timestamp = $moment(fItems2[i].timestamp).format('MMMM Do YYYY, h:mm A');
            }
           
            $scope.setSurvey(fItems2);
            
        });
    }
    

    
    

    vm.showFilterBar = function () {
      filterBarInstance = $ionicFilterBar.show({
        items: vm.items,
        update: function (filteredItems) {
          vm.items = filteredItems;
        },
        filterProperties: 'patient'
      }); 
    };
 
  $ionicModal.fromTemplateUrl('pat-detail-modal.html', {
    scope: $scope,
    animation: 'slide-in-up'
  }).then(function(modal) {
    $scope.modal = modal
  })  

  vm.openModal = function(pats, date, dr, dob, username, password) {
    $scope.modal.show()
    console.log(pats);
    $scope.pats1 = pats;
    $scope.date1 = date;
    $scope.dr1 = dr;
    $scope.pic = "img/headshots/headshot" + Math.floor((Math.random() * 10) + 1) + ".jpg";
    $scope.dateP = "Select Date";
    $scope.timeP = "Select Time";
    $scope.labSelect = "";
    $scope.dob = dob;
    $scope.username = username;
    $scope.password = password;
    console.log(dob);
    $scope.extraMessage = "";
    //$scope.vitalItems = $scope.getVitalItems();
    //$scope.vitalItems = $scope.getVitalItems();
      
      $scope.getVitalItems();
      $scope.getForms();
      $scope.getSurveys();
  }

  vm.closeModal = function() {
    $scope.modal.hide();
  };

  $scope.$on('$destroy', function() {
    $scope.modal.remove();
  });
    
      var ipObj1 = {
      callback: function (val) {  //Mandatory
        console.log('Return value from the datepicker popup is : ' + val, new Date(val));
        var d1 = new Date(val);
        var days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        $scope.finalDate = (d1.getMonth() + 1) + "-" + (d1.getDate()) + "-" + (d1.getFullYear());
        $scope.dateP = (months[d1.getMonth() + 1]) + "-" + (d1.getDate()) + "-" + (d1.getFullYear());
      },
      disabledDates: [            //Optional
        new Date(2016, 2, 16),
        new Date(2015, 3, 16),
        new Date(2015, 4, 16),
        new Date(2015, 5, 16),
        new Date('Wednesday, August 12, 2015'),
        new Date("08-16-2016"),
        new Date(1439676000000)
      ],
      from: new Date(2016, 1, 1), //Optional
      to: new Date(2020, 12, 30), //Optional
      inputDate: new Date(),      //Optional
      mondayFirst: true,          //Optional
      disableWeekdays: [0],       //Optional
      closeOnSelect: false,       //Optional
      templateType: 'popup'       //Optional
    };

    $scope.openDatePicker = function(){
      ionicDatePicker.openDatePicker(ipObj1);
    };
    
    $scope.timePickerObject = {
      inputEpochTime: ((new Date()).getHours() * 60 * 60),  //Optional
      step: 15,  //Optional
      format: 12,  //Optional
      titleLabel: 'Appointment Time',  //Optional
      setLabel: 'Set',  //Optional
      closeLabel: 'Close',  //Optional
      setButtonType: 'button-positive',  //Optional
      closeButtonType: 'button-stable',  //Optional
      callback: function (val) {    //Mandatory
        timePickerCallback(val);
      }
    };
    
    function timePickerCallback(val) {
      if (typeof (val) === 'undefined') {
        console.log('Time not selected');
      } else {
        var selectedTime = new Date(val * 1000);
        console.log('Selected epoch is : ', val, 'and the time is ', selectedTime.getUTCHours(), ':', selectedTime.getUTCMinutes(), 'in UTC');
        var suffix = (selectedTime.getUTCHours() >= 12)? 'pm' : 'am';
        var minutes = (selectedTime.getUTCMinutes() == 0)? '00' : selectedTime.getUTCMinutes();
        $scope.timeP = ((selectedTime.getUTCHours() + 11) % 12 + 1) + ':' + minutes + " " + suffix.toUpperCase();
      }
    }
    
    $scope.pickLab = function(theLab){
        $scope.labSelect = theLab;
        console.log(theLab);
    }
    
    $scope.extraMsg = function(msg){
        $scope.extraMessage = msg;
    }
    
    $scope.sendPush = function(){
        
        if($scope.username && $scope.timeP != "Select Time" && $scope.dateP != "Select Date" && $scope.pats1){
            
            var link = '< TODO add_url_here >'
            + "username=" + $scope.username + "&"
            + "time=" + $scope.timeP + "&"
            + "message=" + $scope.dateP + "&"
            +"extraMsg=" + $scope.extraMessage + "&"
            +"patName=" + $scope.pats1;

            console.log(link);

                $http.get(link).then(function (response){
                    console.log(response.data);
                    alert("Patient Registration Complete");
                    $scope.extraMessage = "";


                });
        }
        else{
            alert("Please complete the form.")
            return;
        }
    }
    $scope.updateLab = function(lb){
        $scope.labTest = lb;
    }
    $scope.updateLoc = function(lc){
        $scope.location = lc;
    }
    $scope.updateInst = function(ins){
        $scope.sInst = ins;
    }
    
    
    $scope.sendLab = function(){
        if($scope.pats1 && $scope.dr1 && $scope.location && $scope.labTest && $scope.sInst && $scope.username){
            var link = '< TODO add_url_here >'
            + "patientName=" + $scope.pats1 + "&"
            + "physicianName=" + $scope.dr1 + "&"
            + "physicianClinicName=" + $scope.location + "&"
            + "Name=" + $scope.labTest + "&"
            + "Special_Instructions=" + $scope.sInst + "&"
            + "username=" + $scope.username;

            $http.get(link).then(function (response){
                    console.log(link);
                    alert("Lab has been sent.")
            });
        }
        else{
            
            alert("Please complete the form."); 
            return;
        }
    }
    
    
    
    
  $ionicModal.fromTemplateUrl('my-modal.html', {
    scope: $scope,
    animation: 'slide-in-up'
  }).then(function(modal) {
    $scope.modal2 = modal;
  });
  $scope.openModal2 = function(tStamp, aDate, pName, drink, smoke, surg, meds, tuber, diabetes, canc, comp, alls) {
      $scope.tStamp = tStamp;
      $scope.aDate = aDate;
      $scope.pName = pName;
      $scope.drink = drink;
      $scope.smoke = smoke;
      $scope.surg = surg;
      $scope.meds = meds;
      $scope.tuber = tuber;
      $scope.diabetes = diabetes;
      $scope.cancer = canc;
      $scope.comp = comp;
      $scope.all = alls;
      console.log("complaints=", comp);
    $scope.modal2.show();
  };
  $scope.closeModal2 = function() {
    $scope.modal2.hide();
  };
    
  $ionicModal.fromTemplateUrl('my-modal2.html', {
    scope: $scope,
    animation: 'slide-in-up'
  }).then(function(modal) {
    $scope.modal3 = modal;
  });
  $scope.openModal3 = function(tStamp, aDate, pName, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10) {
      $scope.pName2 = pName;
      $scope.tStamp2 = tStamp;
      $scope.aDate2 = aDate;
      $scope.q1 = q1;
      $scope.q2 = q2;
      $scope.q3 = q3;
      $scope.q4 = q4;
      $scope.q5 = q5;
      $scope.q6 = q6;
      $scope.q7 = q7;
      $scope.q8 = q8;
      $scope.q9 = q9;
      $scope.q10 = q10;


    $scope.modal3.show();
  };
  $scope.closeModal3 = function() {
    $scope.modal3.hide();
  };
    
    return vm;  

})
   
.controller('appointmentReminderCtrl', function($scope) {

})
   
.controller('sendLabsCtrl', function($scope) {

})
      
.controller('registerPatientCtrl', function($scope, $http, $moment) {
    var r = this;
    r.updateDr = function(dr){
        r.dr = dr;
    }
    
    r.regPat = function(){
        
        if(r.firstName && r.lastName && r.dr && r.dob && r.username && r.password){
            
            var link = '< TODO add_url_here >' 
            + "date=" + "2016-05-09"+ "&" 
            + "name=" + r.firstName + " " + r.lastName + "&"
            + "dr=" + r.dr + "&"
            + "dob=" + r.dob + "&"
            + "username=" + r.username + "&"
            + "password=" + r.password + "&"
            + "token=" + "t2";

            console.log(link);

            $http.get(link).then(function (response){
                    //$scope.status = $scope.giveResponse(response.data.status);
                console.log(response.data);
                emailjs.send("gmail","template_X28egazR",
                             {to_email: r.username,
                              to_name: r.firstName + " " + r.lastName, 
                              to_password: r.password});
                alert("Patient Registration Complete");


            });
        }
        else{
            alert("Please fill out form completely.");
            return;
        }
    }
    

})
   
.controller('clinicInformationCtrl', function($scope, $http) {
        var c = this;
    
        $scope.getInfo = function(){
    
            $http.get('< TODO add_url_here >').then(function(response){
                $scope.clinicAddress = response.data.clinicAddress;
                $scope.clinicPhone = response.data.clinicPhone;
                $scope.clinicName = response.data.clinicName;
                $scope.clinicHours = response.data.clinicHours;
                $scope.clinicEmail = response.data.clinicEmail;
                console.log(response.data);

            }, function(error){
            //there was an error fetching from the server
            });
        }
        $scope.getInfo();
        
        c.submitClinicInfo = function(){
            if(c.clinicAddress && c.clinicName && c.clinicPhone && c.clinicHours && c.clinicEmail){
                console.log(c.clinicName);
                console.log(c.clinicPhone);
                console.log(c.clinicAddress);
                console.log(c.clinicEmail);
                console.log(c.clinicHours);
                var link = '< TODO add_url_here >' 
                + "clinicAddress=" + c.clinicAddress + "&" 
                + "clinicName=" + c.clinicName + "&"
                + "clinicPhone=" + c.clinicPhone + "&"
                + "clinicHours=" + c.clinicHours + "&"
                + "clinicEmail=" + c.clinicEmail;

                console.log(link);

                $http.get(link).then(function (response){
                    //$scope.status = $scope.giveResponse(response.data.status);
                    alert("Clinic Information Update Complete");
                    $scope.getInfo();

                });
            }
            else{
                alert("Please fill out form completely.");
                return; 
            }
        }

})
 