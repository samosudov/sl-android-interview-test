package work.samosudov.sltestapp.feature.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import work.samosudov.sltestapp.data.repository.DatabaseRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    val coordinateResult = MutableLiveData<LatLng>()
    val serviceCoordinateResult = MutableLiveData<LatLng>()
    private val valve: BehaviorSubject<Boolean> = BehaviorSubject.create()
    private val subscriptions = CompositeDisposable()

    init {
        subscribeOnCoordinates()
    }

    private fun subscribeOnCoordinates() {
        subscriptions.add(
            databaseRepository
                .getCoordinate()
                .concatMap {
                    Observable.just(it).delay(5, TimeUnit.SECONDS)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    coordinateResult.value = LatLng(it.lat, it.lng)
                }
                .buffer(3)
                .map { it.last() }
                .doOnNext {
                    serviceCoordinateResult.value = LatLng(it.lat, it.lng)
                }
                .subscribe(
                    {
                        println("subscribeOnCoordinates subscribe =${it}")
                    },
                    {
                        println("subscribeOnCoordinates error =${it.message}")
                    }
                )
        )
    }

    fun switchStream(isOn: Boolean) {
        valve.onNext(isOn)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

}