import axios from 'axios';
import React, { useState } from 'react';
import { useAuth } from '../providers/AuthProvider';

// Reusable Card Component
const OnboardingCard = ({ 
  step, 
  totalSteps, 
  title, 
  children, 
  onNext, 
  onSkip, 
  isNextDisabled, 
  isSkippable,  
}) => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-700 via-blue-900 to-slate-800 flex items-center justify-center p-4">
      <div className="bg-white rounded-3xl shadow-2xl w-full max-w-md p-8">
        {/* Header */}
        <div className="text-center mb-6">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">{title}</h2>
        </div>

        {/* Progress Bar */}
        <div className="mb-8">
          <div className="flex justify-between mb-2">
            {[...Array(totalSteps)].map((_, idx) => (
              <div
                key={idx}
                className={`h-2 flex-1 mx-1 rounded-full transition-all duration-300 ${
                  idx < step ? 'bg-cyan-400' : 'bg-gray-200'
                }`}
              />
            ))}
          </div>
          <p className="text-center text-sm text-gray-600">
            Step {step} of {totalSteps}
          </p>
        </div>

        {/* Content */}
        <div className="mb-8">{children}</div>

        {/* Buttons */}
        <div className="flex gap-3">
          {isSkippable && (
            <button
              onClick={onSkip}
              className="flex-1 py-3 px-6 rounded-xl border-2 border-gray-300 text-gray-600 font-semibold hover:bg-gray-50 transition-colors"
            >
              Skip
            </button>
          )}
          <button
            onClick={onNext}
            disabled={isNextDisabled}
            className={`flex-1 py-3 px-6 rounded-xl font-semibold transition-all ${
              isNextDisabled
                ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                : 'bg-cyan-400 text-white hover:bg-cyan-500 shadow-lg hover:shadow-xl'
            }`}
          >
            {step === totalSteps ? 'Finish' : 'Next'}
          </button>
        </div>
      </div>
    </div>
  );
};

// Main Onboarding Flow
const GymOnboarding = () => {
  const [step, setStep] = useState(1);
  const {token,setToken,userDetails}=useAuth();
  const [formData, setFormData] = useState({
    userId:'',
    username:'',
    gender: '',
    dob: '',
    longitude: '',
    latitude:'',
    profilePic: null,
  });

  const totalSteps = 5;

  const handleNext = async () => {
    if (step < totalSteps) {      
       setStep(step + 1);
    } else {
      console.log('Form submitted:', formData);
      // Handle final submission,
       formData.userId=userDetails.userId;
       formData.username=userDetails.username;
      const handleSubmit = async ()=>{
        const res= await axios.put("http://localhost:8080/upateUserProfile", formData,{
             headers:{
              Authorization:`Bearer ${token}`
          }}    
          );
        console.log(res.data);
      }
      handleSubmit();
    }
  };

  const handleSkip = () => {
    setStep(step + 1);
  };

 const updateFormData = (field, value) => {
  setFormData((prev) => ({
    ...prev,
    [field]: value
  }));
};

  // Get user's location using Geolocation API
  const handleGetLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          // You can use reverse geocoding API here to get location name
          alert(`Location captured: ${latitude}, ${longitude}`);
          if(latitude && longitude){
            updateFormData('latitude', latitude);
            updateFormData('longitude',longitude);
          }
          console.log(formData)
        },
        (error) => {
          alert('Unable to get location. Please enter manually.');
        }
      );
    } else {
      alert('Geolocation is not supported by your browser.');
    }
  };

  const handleProfilePicChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        updateFormData('profilePic', reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  // Step 1: Welcome
  if (step === 1) {
    return (
      <OnboardingCard
        step={step}
        totalSteps={totalSteps}
        title={`Welcome, ${formData.username || 'there'}! 🚀`}
        onNext={handleNext}
        isNextDisabled={false}
        isSkippable={false}
      >
        <div className="text-center">
          <div className="w-32 h-32 mx-auto mb-4 bg-gradient-to-br from-cyan-400 to-blue-500 rounded-full flex items-center justify-center">
            <span className="text-5xl">👋</span>
          </div>
          <p className="text-gray-600">
            Let's set up your profile to get started with your fitness journey!
          </p>
        </div>
      </OnboardingCard>
    );
  }

  // Step 2: Username
  // if (step === 2) {
  //   return (
  //     <OnboardingCard
  //       step={step}
  //       totalSteps={totalSteps}
  //       title="What's your name?"
  //       onNext={handleNext}
  //       isNextDisabled={!formData.username}
  //       isSkippable={false}
  //     >
  //       <input
  //         type="text"
  //         value={formData.username}
  //         onChange={(e) => updateFormData('username', e.target.value)}
  //         placeholder="Enter your full name"
  //         className="w-full px-4 py-3 border-2 border-gray-300 rounded-xl focus:outline-none focus:border-cyan-400 transition-colors"
  //       />
  //     </OnboardingCard>
  //   );
  // }

  // Step 3: Gender
  if (step === 2) {
    return (
      <OnboardingCard
        step={step}
        totalSteps={totalSteps}
        title="Select your gender"
        onNext={handleNext}
        isNextDisabled={!formData.gender}
        isSkippable={false}
      >
        <div className="space-y-3">
          {['Male', 'Female', 'Other'].map((option) => (
            <button
              key={option}
              onClick={() => updateFormData('gender', option)}
              className={`w-full py-3 px-6 rounded-xl border-2 font-semibold transition-all ${
                formData.gender === option
                  ? 'bg-cyan-400 text-white border-cyan-400'
                  : 'bg-white text-gray-700 border-gray-300 hover:border-cyan-400'
              }`}
            >
              {option}
            </button>
          ))}
        </div>
      </OnboardingCard>
    );
  }

  // Step 4: Date of Birth
  if (step === 3) {
    return (
      <OnboardingCard
        step={step}
        totalSteps={totalSteps}
        title="When were you born?"
        onNext={handleNext}
        isNextDisabled={!formData.dob}
        isSkippable={false}
      >
        <input
          type="date"
          value={formData.dob}
          onChange={(e) => updateFormData('dob', e.target.value)}
          className="w-full px-4 py-3 border-2 border-gray-300 rounded-xl focus:outline-none focus:border-cyan-400 transition-colors"
        />
      </OnboardingCard>
    );
  }

  // Step 4: location/Location
  if (step === 4) {
    return (
      <OnboardingCard
        step={step}
        totalSteps={totalSteps}
        title="Where are you located?"
        onNext={handleNext}
        isNextDisabled={!formData.latitude}
        isSkippable={false}
      >
        <div className="space-y-3">
          <input
            type="text"
            // value={`${formData.latitude}`}
              // onChange={(e) => updateFormData('location', e.target.value)}
            placeholder="Enter your location"
            className="w-full px-4 py-3 border-2 border-gray-300 rounded-xl focus:outline-none focus:border-cyan-400 transition-colors"
          />
          <button
            onClick={handleGetLocation}
            className="w-full py-3 px-6 rounded-xl bg-gray-100 text-gray-700 font-semibold hover:bg-gray-200 transition-colors flex items-center justify-center gap-2"
          >
            📍 Use my location
          </button>
        </div>
      </OnboardingCard>
    );
  }

  // Step 6: Profile Picture (Optional)
  if (step === 5) {
    return (
      <OnboardingCard
        step={step}
        totalSteps={totalSteps}
        title="Add a profile picture"
        onNext={handleNext}
        isNextDisabled={false}
        isSkippable={true}
        onSkip={handleSkip}
      >
        <div className="text-center">
          <div className="w-32 h-32 mx-auto mb-4 rounded-full overflow-hidden bg-gray-200 flex items-center justify-center">
            {formData.profilePic ? (
              <img
                src={formData.profilePic}
                alt="Profile"
                className="w-full h-full object-cover"
              />
            ) : (
              <span className="text-5xl">📷</span>
            )}
          </div>
          <label className="cursor-pointer inline-block py-3 px-6 bg-cyan-400 text-white rounded-xl font-semibold hover:bg-cyan-500 transition-colors">
            {formData.profilePic ? 'Change Picture' : 'Upload Picture'}
            <input
              type="file"
              accept="image/*"
              onChange={handleProfilePicChange}
              className="hidden"
            />
          </label>
        </div>
      </OnboardingCard>
    );
  }

  return null;
};

export default GymOnboarding;
