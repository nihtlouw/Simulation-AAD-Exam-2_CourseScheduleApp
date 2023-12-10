package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference

        val keyPref = getString(R.string.pref_key_dark)
        val switchTheme = findPreference<ListPreference>(keyPref)

        switchTheme?.setOnPreferenceChangeListener { _, newVal ->
            val modeNight = NightMode.valueOf(newVal.toString().uppercase(Locale.US))
            updateTheme(modeNight.value)
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference

        val notifPref = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        val reminderDaily = DailyReminder()
        notifPref?.setOnPreferenceChangeListener { _, newVal ->

            if (newVal == true){
                reminderDaily.setDailyReminder(requireContext())
            }else{
                reminderDaily.cancelAlarm(requireContext())
            }

            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}