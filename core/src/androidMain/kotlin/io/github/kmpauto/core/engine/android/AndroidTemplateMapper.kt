package io.github.kmpauto.core.engine.android

import android.content.Context
import androidx.car.app.CarContext
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.CarColor
import androidx.car.app.model.CarIcon
import androidx.car.app.model.GridItem
import androidx.car.app.model.GridTemplate
import androidx.car.app.model.Header
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.MessageTemplate
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.SectionedItemList
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat
import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoGridItem
import io.github.kmpauto.core.components.AutoIcon
import io.github.kmpauto.core.components.AutoListItem
import io.github.kmpauto.core.components.AutoListSection
import io.github.kmpauto.core.components.AutoPaneItem
import io.github.kmpauto.core.template.AutoGridTemplate
import io.github.kmpauto.core.template.AutoListTemplate
import io.github.kmpauto.core.template.AutoMessageTemplate
import io.github.kmpauto.core.template.AutoPaneTemplate
import io.github.kmpauto.core.template.AutoTemplate

/**
 * Maps unified [AutoTemplate] models into native Android Auto [Template] components.
 */
public object AndroidTemplateMapper {

    public fun toAndroidTemplate(autoTemplate: AutoTemplate, carContext: CarContext): Template {
        return when (autoTemplate) {
            is AutoListTemplate -> toListTemplate(autoTemplate, carContext)
            is AutoGridTemplate -> toGridTemplate(autoTemplate, carContext)
            is AutoPaneTemplate -> toPaneTemplate(autoTemplate, carContext)
            is AutoMessageTemplate -> toMessageTemplate(autoTemplate, carContext)
        }
    }

    private fun createHeader(
        title: String?,
        headerAction: AutoAction?,
        carContext: CarContext
    ): Header? {
        if (title == null && headerAction == null) return null
        val builder = Header.Builder()
        title?.let { builder.setTitle(it) }
        headerAction?.let { builder.setStartHeaderAction(toAndroidAction(it, carContext)) }
        return builder.build()
    }

    public fun toListTemplate(template: AutoListTemplate, carContext: CarContext): ListTemplate {
        val builder = ListTemplate.Builder()
            .setLoading(template.isLoading)

        createHeader(template.title, template.headerAction, carContext)?.let {
            builder.setHeader(it)
        }

        if (template.actionStrip.isNotEmpty()) {
            builder.setActionStrip(toActionStrip(template.actionStrip, carContext))
        }

        if (!template.isLoading) {
            val sections = template.sections
            if (sections.size == 1 && sections.first().title == null) {
                val itemListBuilder = ItemList.Builder()
                sections.first().items.forEach { item ->
                    itemListBuilder.addItem(toRow(item, carContext))
                }
                builder.setSingleList(itemListBuilder.build())
            } else if (sections.isNotEmpty()) {
                sections.forEach { section ->
                    val itemListBuilder = ItemList.Builder()
                    section.items.forEach { item ->
                        itemListBuilder.addItem(toRow(item, carContext))
                    }
                    builder.addSectionedList(
                        SectionedItemList.create(
                            itemListBuilder.build(),
                            section.title ?: ""
                        )
                    )
                }
            }
        }

        return builder.build()
    }

    public fun toGridTemplate(template: AutoGridTemplate, carContext: CarContext): GridTemplate {
        val builder = GridTemplate.Builder()
            .setLoading(template.isLoading)

        createHeader(template.title, template.headerAction, carContext)?.let {
            builder.setHeader(it)
        }

        if (template.actionStrip.isNotEmpty()) {
            builder.setActionStrip(toActionStrip(template.actionStrip, carContext))
        }

        if (!template.isLoading) {
            val itemListBuilder = ItemList.Builder()
            template.items.forEach { item ->
                itemListBuilder.addItem(toGridItem(item, carContext))
            }
            builder.setSingleList(itemListBuilder.build())
        }

        return builder.build()
    }

    public fun toPaneTemplate(template: AutoPaneTemplate, carContext: CarContext): PaneTemplate {
        val paneBuilder = Pane.Builder()
            .setLoading(template.isLoading)

        if (!template.isLoading) {
            template.items.forEach { item ->
                paneBuilder.addRow(toPaneRow(item))
            }
            template.actions.forEach { action ->
                paneBuilder.addAction(toAndroidAction(action, carContext))
            }
        }

        val builder = PaneTemplate.Builder(paneBuilder.build())

        createHeader(template.title, template.headerAction, carContext)?.let {
            builder.setHeader(it)
        }

        if (template.actionStrip.isNotEmpty()) {
            builder.setActionStrip(toActionStrip(template.actionStrip, carContext))
        }

        return builder.build()
    }

    public fun toMessageTemplate(template: AutoMessageTemplate, carContext: CarContext): MessageTemplate {
        val builder = MessageTemplate.Builder(template.message)
            .setLoading(template.isLoading)

        template.debugMessage?.let { builder.setDebugMessage(it) }
        template.icon?.let { builder.setIcon(toCarIcon(it, carContext)) }

        createHeader(template.title, template.headerAction, carContext)?.let {
            builder.setHeader(it)
        }

        if (template.actionStrip.isNotEmpty()) {
            builder.setActionStrip(toActionStrip(template.actionStrip, carContext))
        }

        template.actions.forEach { action ->
            builder.addAction(toAndroidAction(action, carContext))
        }

        return builder.build()
    }

    public fun toRow(item: AutoListItem, carContext: CarContext): Row {
        val builder = Row.Builder().setTitle(item.title)
        item.subtitle?.let { builder.addText(it) }
        item.icon?.let { builder.setImage(toCarIcon(it, carContext)) }
        item.onClick?.let { click ->
            builder.setOnClickListener { click() }
        }
        return builder.build()
    }

    public fun toGridItem(item: AutoGridItem, carContext: CarContext): GridItem {
        val builder = GridItem.Builder()
            .setTitle(item.title)
            .setImage(toCarIcon(item.icon, carContext))

        item.subtitle?.let { builder.setText(it) }
        item.onClick?.let { click ->
            builder.setOnClickListener { click() }
        }
        return builder.build()
    }

    public fun toPaneRow(item: AutoPaneItem): Row {
        val builder = Row.Builder().setTitle(item.title)
        item.text?.let { builder.addText(it) }
        return builder.build()
    }

    public fun toAndroidAction(action: AutoAction, carContext: CarContext): Action {
        return when (action) {
            is AutoAction.Back -> Action.BACK
            is AutoAction.AppIcon -> Action.APP_ICON
            is AutoAction.Primary -> {
                Action.Builder()
                    .setTitle(action.title)
                    .setBackgroundColor(CarColor.PRIMARY)
                    .setOnClickListener { action.onClick() }
                    .build()
            }
            is AutoAction.Standard -> {
                Action.Builder()
                    .setTitle(action.title)
                    .setOnClickListener { action.onClick() }
                    .build()
            }
            is AutoAction.Destructive -> {
                Action.Builder()
                    .setTitle(action.title)
                    .setBackgroundColor(CarColor.RED)
                    .setOnClickListener { action.onClick() }
                    .build()
            }
        }
    }

    public fun toActionStrip(actions: List<AutoAction>, carContext: CarContext): ActionStrip {
        val builder = ActionStrip.Builder()
        actions.take(4).forEach { action ->
            builder.addAction(toAndroidAction(action, carContext))
        }
        return builder.build()
    }

    public fun toCarIcon(icon: AutoIcon, context: Context): CarIcon {
        return when (icon) {
            is AutoIcon.Resource -> {
                val resId = context.resources.getIdentifier(
                    icon.resName,
                    "drawable",
                    context.packageName
                )
                if (resId != 0) {
                    CarIcon.Builder(IconCompat.createWithResource(context, resId)).build()
                } else {
                    CarIcon.APP_ICON
                }
            }
            is AutoIcon.SystemIcon -> {
                CarIcon.APP_ICON
            }
        }
    }
}
