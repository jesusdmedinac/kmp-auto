package io.github.kmpauto.sample.screens

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoIcon
import io.github.kmpauto.core.components.AutoListItem
import io.github.kmpauto.core.components.AutoListSection
import io.github.kmpauto.core.components.AutoPaneItem
import io.github.kmpauto.core.navigation.AutoNavigator
import io.github.kmpauto.core.navigation.AutoScreen
import io.github.kmpauto.core.template.AutoListTemplate
import io.github.kmpauto.core.template.AutoMessageTemplate
import io.github.kmpauto.core.template.AutoPaneTemplate
import io.github.kmpauto.core.template.AutoTemplate
import io.github.kmpauto.sample.model.ChargingStation

/**
 * In-car list screen displaying nearby EV charging stations.
 */
public class ChargingStationListScreen(
    public val navigator: AutoNavigator
) : AutoScreen() {

    private val stations = listOf(
        ChargingStation(
            id = "cs-1",
            name = "Reforma Supercharger",
            powerKw = 250,
            availablePlugs = 4,
            totalPlugs = 8,
            address = "Paseo de la Reforma 222, CDMX",
            pricePerKwh = "$4.50 MXN"
        ),
        ChargingStation(
            id = "cs-2",
            name = "Polanco Fast Hub",
            powerKw = 350,
            availablePlugs = 2,
            totalPlugs = 6,
            address = "Campos Elíseos 204, Polanco",
            pricePerKwh = "$5.20 MXN"
        ),
        ChargingStation(
            id = "cs-3",
            name = "Insurgentes Sur Station",
            powerKw = 150,
            availablePlugs = 5,
            totalPlugs = 5,
            address = "Insurgentes Sur 1602, Crédito Constructor",
            pricePerKwh = "$3.80 MXN"
        )
    )

    override fun render(): AutoTemplate {
        val items = stations.map { station ->
            AutoListItem(
                title = station.name,
                subtitle = "${station.powerKw} kW • ${station.availablePlugs}/${station.totalPlugs} libres",
                icon = AutoIcon.Resource("ic_ev_station"),
                onClick = {
                    navigator.push(ChargingStationDetailScreen(station, navigator))
                }
            )
        }

        return AutoListTemplate(
            title = "Estaciones de Carga",
            headerAction = AutoAction.AppIcon,
            sections = listOf(
                AutoListSection(title = "Cercanas a tu ruta", items = items)
            )
        )
    }
}

/**
 * In-car detail screen with key-value information and charging CTAs.
 */
public class ChargingStationDetailScreen(
    public val station: ChargingStation,
    public val navigator: AutoNavigator
) : AutoScreen() {

    override fun render(): AutoTemplate {
        val items = listOf(
            AutoPaneItem(title = "Ubicación", text = station.address),
            AutoPaneItem(title = "Potencia Máxima", text = "${station.powerKw} kW Ultra-Fast"),
            AutoPaneItem(title = "Disponibilidad", text = "${station.availablePlugs} de ${station.totalPlugs} conectores libres"),
            AutoPaneItem(title = "Tarifa Estimada", text = "${station.pricePerKwh} / kWh")
        )

        val actions = listOf(
            AutoAction.Primary(title = "Iniciar Carga") {
                navigator.push(ChargingActiveScreen(station, navigator))
            },
            AutoAction.Standard(title = "Volver") {
                navigator.pop()
            }
        )

        return AutoPaneTemplate(
            title = station.name,
            headerAction = AutoAction.Back,
            items = items,
            actions = actions
        )
    }
}

/**
 * In-car active session screen showing live progress and controls.
 */
public class ChargingActiveScreen(
    public val station: ChargingStation,
    public val navigator: AutoNavigator
) : AutoScreen() {

    private var deliveredKwh = 18.5

    override fun render(): AutoTemplate {
        return AutoMessageTemplate(
            title = "Carga en Curso",
            message = "Cargando en ${station.name}. Energía entregada: ${deliveredKwh} kWh.",
            debugMessage = "station_id=${station.id};status=CHARGING",
            icon = AutoIcon.Resource("ic_charging_active"),
            actions = listOf(
                AutoAction.Destructive(title = "Detener Carga") {
                    navigator.popToRoot()
                },
                AutoAction.Standard(title = "Ver Estaciones") {
                    navigator.popToRoot()
                }
            )
        )
    }
}
